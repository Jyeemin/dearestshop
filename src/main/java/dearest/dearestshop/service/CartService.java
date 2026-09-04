package dearest.dearestshop.service;


import dearest.dearestshop.domain.cart.Cart;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.dto.cartdto.CartAddDto;
import dearest.dearestshop.dto.cartdto.CartListDto;
import dearest.dearestshop.repository.CartItemRepository;
import dearest.dearestshop.repository.CartRepository;
import dearest.dearestshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;

    /**
     * 카트에 상품 추가
     * @param cartAddDto
     * @return
     */
    @Transactional
    public Long addCart(CartAddDto cartAddDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findOne(authentication.getName());

        Cart cart = cartRepository.findByMember(member)
                .orElse(null);

        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Product product = productRepository.findById(cartAddDto.getProductId()).orElseThrow(() -> {
            return new RuntimeException("상품이 존재하지 않음");
        });

        CartItem cartItem = CartItem.createCartItem(product, cartAddDto.getQuantity(), cartAddDto.getSize());
        cart.addCartItem(cartItem);
        return cartItem.getId();
    }

    /**
     * 카트 아이템 조회
     * @return
     */
    public List<CartListDto> cartList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findOne(authentication.getName());

        Cart cart = cartRepository.findByMember(member).orElseThrow(() -> {
            return new RuntimeException("카트가 존재하지 않음");
        });


        return cart.getCartItems()
                .stream()
                .map(cartItem -> {

                    String thumbnail =
                            cartItem.getProduct()
                                    .getImages()
                                    .stream()
                                    .filter(image ->
                                            image.getImageType()
                                                    == ImageType.THUMBNAIL
                                    )
                                    .findFirst()
                                    .map(image ->
                                            image.getFileInfo().getImgUrl()
                                    )
                                    .orElse(null);

                    return new CartListDto(
                            cartItem.getId(),
                            cartItem.getProduct().getId(),
                            cartItem.getProduct().getProductName(),
                            cartItem.getProduct().getPrice(),
                            thumbnail,
                            cartItem.getProductSize(),
                            cartItem.getQuantity()
                    );
                })
                .toList();
    }

    /**
     * 카트 상품 수량 변경
     * @param cartItemId
     * @param quantity
     */
    @Transactional
    public void updateQuantity(Long cartItemId, int quantity) {
        if (quantity < 1) {
            throw new RuntimeException("수량은 1개 이상이어야 합니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> {
                    return new RuntimeException("카트에 존재하지 않는 상품입니다.");
                }
        );

        cartItem.changeQuantity(quantity);
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> {
                    return new RuntimeException("카트에 존재하지 않는 상품입니다.");
                }
        );
        cartItemRepository.delete(cartItem);
    }


}
