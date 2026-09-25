package dearest.dearestshop.service;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.order.OrderItem;
import dearest.dearestshop.domain.order.OrderStatus;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductImage;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.dto.orderdto.*;
import dearest.dearestshop.repository.*;
import dearest.dearestshop.repository.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderQueryRepository orderQueryRepository;
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private final DeliveryService deliveryService;
    private final CartItemRepository cartItemRepository;

    /**
     + 주문 추가
     * @param orderCreateDto
     * @return
     */
    @Transactional
    public Long addOrder(OrderCreateDto orderCreateDto) {
        Member member = memberService.getLoginMember();
        Address address;

        if (orderCreateDto.getAddressId() != null) {
            address = addressRepository.findById(
                    orderCreateDto.getAddressId()).orElseThrow(
                    () -> new RuntimeException("주소가 존재하지 않습니다.")
            );
        } else {
            if (orderCreateDto.getNewAddress() == null) {
                throw new RuntimeException("배송지를 선택해주세요.");
            }

            Long newAddress = addressService.createAddress(orderCreateDto.getNewAddress());
            address = addressRepository.findById(newAddress).orElseThrow(
                    () -> new RuntimeException("새 주소 생성에 실패했습니다.")
            );
        }

        //배송지 생성
        Long deliveryId = deliveryService.addDelivery(address.getAddressId(), orderCreateDto.getReceiverName(), orderCreateDto.getDeliveryMessage());

        List<Long> cartItemIds = orderCreateDto.getCartItemIds();
        System.out.println("cartItemIds = " + cartItemIds);


        List<CartItem> allById = cartItemRepository.findAllById(cartItemIds);
        System.out.println("조회된 cartItems = " + allById.size());

        List<OrderItem> orderItems = allById.stream().map(
                cartItem -> OrderItem.createOrderItem(cartItem.getProduct(),
                        cartItem.getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getProductSize())
        ).toList();
        System.out.println("생성된 orderItems = " + orderItems.size());

        for (OrderItem orderItem : orderItems) {
            orderItem.getProduct().increaseSalesCount(
                    orderItem.getQuantity()
            );

            orderItem.getProduct().decreaseStockQuantity(orderItem.getQuantity());
        }

        Order order = Order.createOrder(member, deliveryService.findOne(deliveryId), orderItems);
        System.out.println("주문에 들어간 orderItems = "
                + order.getOrderItems().size());
        orderRepository.save(order);

        cartItemRepository.deleteAll(allById);

        return order.getId();
    }

    /**
     * 전체주문조회
     * @param condition
     * @return
     */
    public List<OrderResponseDto> orders(OrderSearchCondition condition) {
        List<Order> orders = orderQueryRepository.searchOrders(condition);


        return orders.stream().map(
                order -> {
                    List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(
                            orderItem -> {

                                String imgUrl = orderItem.getProduct()
                                        .getImages()
                                        .stream()
                                        .filter(productImage ->
                                                productImage.getImageType() == ImageType.THUMBNAIL)
                                        .map(ProductImage::getFileInfo)
                                        .map(fileInfo -> fileInfo.getImgUrl())
                                        .findFirst()
                                        .orElse(null);

                                return new OrderItemDto(
                                        orderItem.getId(),
                                        orderItem.getProduct().getId(),
                                        orderItem.getProduct().getProductName(),
                                        imgUrl,
                                        orderItem.getSize(),
                                        orderItem.getOrderPrice(),
                                        orderItem.getQuantity()
                                );

                            }
                    ).toList();

                    int totalPrice = orderItemDtos.stream().mapToInt(
                            o -> o.getPrice() * o.getQuantity()).sum();


                    return new OrderResponseDto(
                            order.getMember().getName(),
                            order.getMember().getEmail(),
                            order.getId(),
                            orderItemDtos,
                            order.getCreatedAt(),
                            order.getOrderStatus(),
                            totalPrice
                    );
                }
        ).toList();

    }

    /**
     * 상세주문조회
     */
    public OrderDetailResponseDto detailOrder(Long orderId) {
        Order order = orderRepository.findOrderDetail(orderId).orElseThrow(() -> new RuntimeException("주문이 존재하지 않습니다."));
        List<OrderItemDto> orderItemdtos = order.getOrderItems().stream().map(
                orderItem -> {


                    String imgUrl = orderItem.getProduct()
                            .getImages()
                            .stream()
                            .filter(productImage ->
                                    productImage.getImageType() == ImageType.THUMBNAIL)
                            .map(ProductImage::getFileInfo)
                            .map(fileInfo -> fileInfo.getImgUrl())
                            .findFirst()
                            .orElse(null);

                    return new OrderItemDto(
                            orderItem.getId(),
                            orderItem.getProduct().getId(),
                            orderItem.getProduct().getProductName(),
                            imgUrl,
                            orderItem.getSize(),
                            orderItem.getOrderPrice(),
                            orderItem.getQuantity()
                    );
                }
        ).toList();
        int totalPrice = orderItemdtos.stream().mapToInt((o) -> o.getQuantity() * o.getPrice()).sum();
        return new OrderDetailResponseDto(order.getMember().getName(),
                                        order.getMember().getEmail(),
                                        order.getId(),
                                        orderItemdtos,
                                        order.getCreatedAt(),
                                        order.getOrderStatus(),
                                        totalPrice,
                                        order.getDelivery().getReceiverName(),
                                        new AddressCreateDto(order.getDelivery().getZoneCode(), order.getDelivery().getRoadAddress(),order.getDelivery().getDetailAddress(), false
                                        ),
                                        order.getDelivery().getDeliveryMessage());
    }


    /**************************************관리자 전용 주문 상태 변경
    /**
     *
     * @param orderId
     * @return
     */
    @Transactional
    public Long cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("주문이 존재하지 않습니다."));

        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new RuntimeException("이미 취소된 주문입니다.");
        }

        if (order.getOrderStatus() == OrderStatus.COMPLETE) {
            throw new RuntimeException("이미 완료된 주문입니다.");
        }

        //재고원상복귀
        for(OrderItem orderItem : order.getOrderItems()){
            Product product = orderItem.getProduct();
            product.increaseStockQuantity(orderItem.getQuantity());
            product.decreaseSalesCount(orderItem.getQuantity());
        }
        order.cancel();
        return orderId;
    }

    @Transactional
    public Long completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("주문이 존재하지 않습니다."));

        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new RuntimeException("이미 취소된 주문입니다.");
        }

        if (order.getOrderStatus() == OrderStatus.COMPLETE) {
            throw new RuntimeException("완료된 주문은 취소할 수 없습니다.");
        }
        order.complete();
        return orderId;
    }

    public List<OrderMyDto> myPage() {
        Member member = memberService.getLoginMember();
        List<Order> orders = orderRepository.findOrderMy(member.getId());


            return orders.stream().map(
                    order -> {
                        int totalPrice = order.getOrderItems().stream().mapToInt(
                                (orderItem) -> orderItem
                                        .getOrderPrice() * orderItem.getQuantity()
                        ).sum();


                        return new OrderMyDto(order.getId(),
                                totalPrice,
                                order.getOrderStatus());
                    }).toList();

    }



}
