package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.*;
import dearest.dearestshop.dto.orderdto.OrderMyDto;
import dearest.dearestshop.dto.productdto.*;
import dearest.dearestshop.repository.CategoryRepository;
import dearest.dearestshop.repository.ProductRepository;
import dearest.dearestshop.repository.WishlistItemRepository;
import dearest.dearestshop.repository.query.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final FileService fileService;
    private final CategoryRepository categoryRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final  MemberService memberService;

    /**
     *
     * 상품 추가
     * @param dto
     * @param images
     * @param productInfoDtos
     * @return
     */
    @Transactional
    public Long createProduct(ProductCreateDto dto, List<MultipartFile> images,
                              List<ProductInfoDto> productInfoDtos) {
        if (images.size() != productInfoDtos.size()) {
            throw new RuntimeException("이미지와 이미지 정보의 개수가 다릅니다.");
        }

        System.out.println("===== SERVICE =====");
        System.out.println("categoryId = " + dto.getCategoryId());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리가 존재하지않습니다."));

        List<ProductImage> productImages = new ArrayList<>();

        //이미지와 이미지정보를 같은 ProductImage로 생성
        for (int i = 0; i < images.size(); i++) {
            MultipartFile multipartFile = images.get(i);
            ProductInfoDto productInfoDto = productInfoDtos.get(i);

            FileInfo fileInfo = fileService.save(multipartFile);

            // 4. ProductImage 생성
            ProductImage productImage =
                    ProductImage.createProductImage(
                            productInfoDto.getImageType(),
                            productInfoDto.getSortOrder(),
                            fileInfo
                    );
            productImages.add(productImage);
        }


        //상품 생성
        Product product = Product.createProduct(
                dto.getProductName(),
                dto.getDetailDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                0,
                dto.getSizes(),
                productImages,
                category
        );

        Product save = productRepository.save(product);
        return save.getId();
    }



    /**
     * 상품조회
     * @return productResponseDto
     */
    public List<ProductResponseDto> findAll(){
        List<Product> products = productRepository.findAllWithCategory();

        Set<Long> wishlistProductIds = new HashSet<>();

        Member member = memberService.getLoginMember();
        wishlistProductIds.addAll(wishlistItemRepository.findProductIdsByMember(member));




        return products.stream()
                .map(product ->{
                        String thumbnailUrl = product.getImages().stream()
                                .filter(productImage -> productImage.getImageType() == ImageType.THUMBNAIL)
                                .findFirst()
                                .map(ProductImage::getFileInfo)
                                .map(FileInfo::getImgUrl)
                                .orElseThrow(() -> new RuntimeException("thumbnail do not exist"));

                        boolean isWishlist = wishlistProductIds.contains(product.getId());

                        return new ProductResponseDto(
                                product.getId(),
                                product.getProductName(),
                                product.getPrice(),
                                thumbnailUrl,
                                product.getCategory().getCategoryName(),
                                isWishlist
                        );

                    }).toList();


    }

    public List<ProductResponseDto> search(String keyword, Long categoryId, String sort) {
        List<Product> searchProducts = productQueryRepository.searchProducts(keyword, categoryId, sort);



        Set<Long> wishlistProductIds = new HashSet<>();

         Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {

            Member member = memberService.getLoginMember();

            wishlistProductIds.addAll(
                    wishlistItemRepository.findProductIdsByMember(member)
            );
        }

        return searchProducts.stream().map(
                product -> {
                    String thumbnailUrl = product.getImages().stream()
                            .filter(productImage -> productImage.getImageType() == ImageType.THUMBNAIL)
                            .findFirst()
                            .map(ProductImage::getFileInfo)
                            .map(fileInfo -> fileInfo.getImgUrl())
                            .orElseThrow(() -> new RuntimeException("thumbnail do not exist"));

                    boolean isWishlist = wishlistProductIds.contains(product.getId());

                    return new ProductResponseDto(
                            product.getId(),
                            product.getProductName(),
                            product.getPrice(),
                            thumbnailUrl,
                            product.getCategory().getCategoryName(),
                            isWishlist
                    );

                }
        ).toList();

    }

    public ProductDetailResponseDto findOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("상품이 존재하지 않습니다.");
                });

        List<String> urls = product.getImages().stream()
                .map((productImage) -> {
                    return productImage.getFileInfo().getImgUrl();
                }).toList();

        return new ProductDetailResponseDto(
                product.getId(),
                product.getProductName(),
                product.getDetailDescription(),
                product.getPrice(),
                product.getSizes(),
                urls);

    }

    public List<CategoryResponseDto> findCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(
                category -> new CategoryResponseDto(
                        category.getId(),
                        category.getCategoryName()
                )
        ).toList();
    }

}

