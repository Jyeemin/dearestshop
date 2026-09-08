package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductImage;
import dearest.dearestshop.domain.wishlist.Wishlist;
import dearest.dearestshop.domain.wishlist.WishlistItem;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.repository.ProductRepository;
import dearest.dearestshop.repository.WishlistItemRepository;
import dearest.dearestshop.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final MemberService memberService;
    private final ProductRepository productRepository;

    /**
     * 위시리스트 추가/삭제
     * @param productId
     */
    @Transactional
    public void toggleWishlist(Long productId) {
        Member member = memberService.getLoginMember();
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new RuntimeException("상품이 존재하지 않습니다.")
        );


        Wishlist wishlist = wishlistRepository.findByMember(member)
                .orElse(null);

        if (wishlist == null) {
            wishlist = Wishlist.createWishlist(member);
            wishlistRepository.save(wishlist);
        }

        Boolean isExists = wishlistItemRepository.existsByWishlistAndProduct(wishlist, product);

        //**중복 클릭 시 중복 방지 !! 중요
        if (isExists) {
            wishlistItemRepository.deleteByWishlistAndProduct(wishlist,product);
        }else {
            WishlistItem wishlistItem = WishlistItem.createWishlistItem(wishlist, product);
            wishlistItemRepository.save(wishlistItem);
        }

        };


    public List<ProductResponseDto> findMyWishlist() {
        Member member = memberService.getLoginMember();

        // 현재 회원의 위시리스트 상품 조회
        List<WishlistItem> wishlistItems =
                wishlistItemRepository.findAllByMember(member);

        // ProductResponseDto로 변환
        return wishlistItems.stream()
                .map(wishlistItem -> {

                    String thumbnailUrl =
                            wishlistItem.getProduct()
                                    .getImages()
                                    .stream()
                                    .filter(productImage ->
                                            productImage.getImageType()
                                                    == ImageType.THUMBNAIL
                                    )
                                    .findFirst()
                                    .map(ProductImage::getFileInfo)
                                    .map(fileInfo -> fileInfo.getImgUrl())
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "thumbnail do not exist"
                                            )
                                    );

                    return new ProductResponseDto(
                            wishlistItem.getProduct().getId(),
                            wishlistItem.getProduct().getProductName(),
                            wishlistItem.getProduct().getPrice(),
                            thumbnailUrl,
                            wishlistItem.getProduct()
                                    .getCategory()
                                    .getCategoryName(),
                            true
                    );
                })
                .toList();
    }


}
