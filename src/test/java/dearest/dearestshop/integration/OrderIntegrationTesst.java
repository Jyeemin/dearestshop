package dearest.dearestshop.integration;
import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.cart.Cart;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.product.Category;
import dearest.dearestshop.domain.product.FileInfo;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductImage;
import dearest.dearestshop.domain.product.ProductSize;
import dearest.dearestshop.dto.orderdto.OrderCreateDto;
import dearest.dearestshop.repository.AddressRepository;
import dearest.dearestshop.repository.CartItemRepository;
import dearest.dearestshop.repository.CartRepository;
import dearest.dearestshop.repository.CategoryRepository;
import dearest.dearestshop.repository.MemberRepository;
import dearest.dearestshop.repository.OrderRepository;
import dearest.dearestshop.repository.ProductRepository;
import dearest.dearestshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class OrderIntegrationTesst {
    @Autowired
    OrderService orderService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    OrderRepository orderRepository;

    private Member member;
    private Address address;
    private Category category;
    private Product product;
    private Cart cart;
    private CartItem cartItem;

    /**
     * 회원 생성 > 로그인 상태 설정 > 주소 생성 > 카테고리 생성 > 상품 생성 > 장바구니,아이템 생성
     */
    @BeforeEach
    void setUp(){
        member = Member.createMember(
                "홍길동",
                "test@test.com",
                "encodedPassword",
                "01012341234"
        );

        memberRepository.save(member);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        member.getEmail(),
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        //로그인 상태 설정

        address = Address.createAddress(
                "12345",
                "서울시 테스트로 10",
                "101호",
                true,
                member
        );

        addressRepository.save(address);

        category = Category.createCategory("TOPS");

        categoryRepository.save(category);

        FileInfo fileInfo = FileInfo.createFileInfo(
                "top.png",
                "top.png",
                100L,
                "/images/top.png"
        );

        ProductImage thumbnail =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo
                );


        product = Product.createProduct(
                "반팔 티셔츠",
                "귀여운 반팔 티셔츠",
                30000,
                10,
                0,
                List.of(ProductSize.S, ProductSize.M),
                List.of(thumbnail),
                category
        );

        productRepository.save(product);

        cart = Cart.createCart(member);

        cartRepository.save(cart);

        cartItem = CartItem.createCartItem(
                product,
                2,
                30000,
                ProductSize.M
        );

        cart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);


    }

@Test
public void 주문추가_성공() throws Exception{
//given

//when

//then
}


}






