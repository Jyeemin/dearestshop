package dearest.dearestshop.integration;
import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.cart.Cart;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.order.OrderStatus;
import dearest.dearestshop.domain.product.Category;
import dearest.dearestshop.domain.product.FileInfo;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductImage;
import dearest.dearestshop.domain.product.ProductSize;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.dto.orderdto.*;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
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
    void setUp() {
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
    public void 주문추가_성공() throws Exception {
//given

        OrderCreateDto orderCreateDto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));


//when
        Long orderId = orderService.addOrder(orderCreateDto);
        Order order = orderRepository.findById(orderId).orElseThrow();
//then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getOrderItems().get(0).getProduct().getId())
                .isEqualTo(product.getId());
        assertThat(
                order.getOrderItems().get(0).getQuantity()
        ).isEqualTo(2);

        assertThat(order.getDelivery()).isNotNull();
        assertThat(order.getDelivery().getRoadAddress())
                .isEqualTo("서울시 테스트로 10");
        assertThat(order.getDelivery().getDetailAddress())
                .isEqualTo("101호");

        assertThat(product.getSalesCount()).isEqualTo(2);

        assertThat(cartItemRepository.findById(cartItem.getId())).isEmpty();


    }

    @Test
    public void 주문추가_새주소로성공() throws Exception{
//given
        AddressCreateDto newAddress = new AddressCreateDto("23456", "서울시 테스트로 11", "202호", true);

        OrderCreateDto orderCreateDto = new OrderCreateDto(
                null,
                "홍길동",
                newAddress,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));


//when
        Long orderId = orderService.addOrder(orderCreateDto);
        Order order = orderRepository.findById(orderId).orElseThrow();
//then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getOrderItems().get(0).getProduct().getId())
                .isEqualTo(product.getId());
        assertThat(
                order.getOrderItems().get(0).getQuantity()
        ).isEqualTo(2);

        assertThat(order.getDelivery()).isNotNull();
        assertThat(order.getDelivery().getRoadAddress())
                .isEqualTo("서울시 테스트로 11");
        assertThat(order.getDelivery().getDetailAddress())
                .isEqualTo("202호");

        assertThat(product.getSalesCount()).isEqualTo(2);

        assertThat(cartItemRepository.findById(cartItem.getId())).isEmpty();

    }





    @Test
    public void 주문생성_배송지없음실패() throws Exception {
//given
        OrderCreateDto dto = new OrderCreateDto(
                null,
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));

//when

//then
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> orderService.addOrder(dto));

        assertThat(runtimeException.getMessage()).isEqualTo("배송지를 선택해주세요.");
    }

    @Test
    public void 주문생성_존재하지않는주소_실패() throws Exception {
        //given
        Long notExistAddressId = address.getAddressId() + 1000;
        OrderCreateDto dto = new OrderCreateDto(
                notExistAddressId,
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        //when

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> orderService.addOrder(dto));

        assertThat(runtimeException.getMessage()).isEqualTo("주소가 존재하지 않습니다.");
    }

    @Test
    public void 주문생성시_판매량증가() throws Exception {
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        //when
        orderService.addOrder(dto);
        //then
        assertThat(product.getSalesCount()).isEqualTo(2);
    }

    @Test
    public void 주문생성시_장바구니삭제() throws Exception {
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        //when

        orderService.addOrder(dto);

        //then
        assertThat(cartItemRepository.findById(cartItem.getId())).isEmpty();
    }

    @Test
    public void 전체주문조회() throws Exception {
        //given
        OrderSearchCondition condition = new OrderSearchCondition();



        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        orderService.addOrder(dto);


        FileInfo fileInfo2 = FileInfo.createFileInfo(
                "top2.png",
                "top2.png",
                100L,
                "/images/top2.png"
        );

        ProductImage thumbnail2 =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo2
                );


        Product product2 = Product.createProduct(
                "반팔 티셔츠",
                "귀여운 반팔 티셔츠",
                20000,
                10,
                0,
                List.of(ProductSize.S, ProductSize.M),
                List.of(thumbnail2),
                category
        );

        productRepository.save(product2);


        CartItem cartItem2 = CartItem.createCartItem(
                product2,
                3,
                20000,
                ProductSize.M
        );

        cart.addCartItem(cartItem2);

        cartItemRepository.save(cartItem2);


        OrderCreateDto dto2 = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem2.getId()));
        orderService.addOrder(dto2);

        //when
        List<OrderResponseDto> result =
                orderService.orders(condition);
        //then
        assertThat(orderRepository.findAll()).hasSize(2);
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getMemberName())
                .isEqualTo("홍길동");

    }

    @Test
    public void 주문취소_성공() throws Exception{
    //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));



        assertThat(product.getStockQuantity())
                .isEqualTo(10);

        Long orderId = orderService.addOrder(dto);
        assertThat(product.getSalesCount())
                .isEqualTo(2);



        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.cancelOrder(findOrder.getId());
    //then
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);

        assertThat(product.getSalesCount())
                .isEqualTo(0);

        assertThat(product.getStockQuantity())
                .isEqualTo(10);
    }

    @Test
    public void 이미취소된주문_취소실패() throws Exception{
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        Long orderId = orderService.addOrder(dto);
        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.cancelOrder(findOrder.getId());
        //then
        assertThrows(RuntimeException.class,
                () -> orderService.cancelOrder(findOrder.getId()));
    }


    @Test
    public void 이미완료된주문_취소실패() throws Exception{
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        Long orderId = orderService.addOrder(dto);
        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.completeOrder(findOrder.getId());
        //then
        assertThrows(RuntimeException.class,
                () -> orderService.cancelOrder(findOrder.getId()));
    }


    @Test
    public void 주문완료_성공() throws Exception{
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        Long orderId = orderService.addOrder(dto);
        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.completeOrder(findOrder.getId());

        Order completedOrder =
                orderRepository.findById(orderId)
                        .orElseThrow();
        //then
        assertThat(completedOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }

    @Test
    public void 이미취소된주문_완료실패() throws Exception{
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        Long orderId = orderService.addOrder(dto);
        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.cancelOrder(findOrder.getId());
        //then
        assertThrows(RuntimeException.class,
                () -> orderService.completeOrder(findOrder.getId()));
    }


    @Test
    public void 이미완료된주문_완료실패() throws Exception{
        //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));
        Long orderId = orderService.addOrder(dto);
        //when
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        orderService.completeOrder(findOrder.getId());
        //then
        assertThrows(RuntimeException.class,
                () -> orderService.completeOrder(findOrder.getId()));
    }

    @Test
    public void 마이페이지_주문조회() throws Exception{
    //given

        OrderCreateDto orderCreateDto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId()));


    //when
        Long orderId = orderService.addOrder(orderCreateDto);
        List<OrderMyDto> orderMyDtos = orderService.myPage();

        assertThat(orderMyDtos).hasSize(1);
    }


    @Test
    public void 주문상세조회() throws Exception{
    //given
        OrderCreateDto dto = new OrderCreateDto(
                address.getAddressId(),
                "홍길동",
                null,
                "문 앞에 놓아주세요",
                List.of(cartItem.getId())
        );

        Long orderId = orderService.addOrder(dto);
    //when
        OrderDetailResponseDto result =
                orderService.detailOrder(orderId);
    //then
        assertThat(result.getOrderId())
                .isEqualTo(orderId);

        assertThat(result.getMemberName())
                .isEqualTo("홍길동");

        assertThat(result.getEmail())
                .isEqualTo("test@test.com");

        assertThat(result.getStatus())
                .isEqualTo(OrderStatus.ORDER);

        assertThat(result.getOrderItemDtos())
                .hasSize(1);

        assertThat(result.getOrderItemDtos().get(0).getProductId())
                .isEqualTo(product.getId());

        assertThat(result.getOrderItemDtos().get(0).getProductName())
                .isEqualTo("반팔 티셔츠");

        assertThat(result.getOrderItemDtos().get(0).getQuantity())
                .isEqualTo(2);

        assertThat(result.getOrderItemDtos().get(0).getPrice())
                .isEqualTo(30000);

        assertThat(result.getTotalPrice())
                .isEqualTo(60000);

        assertThat(result.getReceiverName())
                .isEqualTo("홍길동");

        assertThat(result.getNewAddress().getRoadAddress())
                .isEqualTo("서울시 테스트로 10");

        assertThat(result.getNewAddress().getDetailAddress())
                .isEqualTo("101호");

        assertThat(result.getDeliveryMessage())
                .isEqualTo("문 앞에 놓아주세요");
    }


    }












    




    
    

    

















