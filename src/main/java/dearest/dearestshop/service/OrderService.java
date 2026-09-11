package dearest.dearestshop.service;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.order.OrderItem;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.dto.orderdto.OrderCreateDto;
import dearest.dearestshop.dto.orderdto.OrderResponseDto;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private final DeliveryService deliveryService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Long addOrder(OrderCreateDto orderCreateDto) {
        Member member = memberService.getLoginMember();
        Address address = null;

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
            addressRepository.findById(newAddress).orElseThrow(
                    () -> new RuntimeException("새 주소 생성에 실패했습니다.")
            );
        }

        //배송지 생성
        Long deliveryId = deliveryService.addDelivery(address.getAddressId(), orderCreateDto.getReceiverName(), orderCreateDto.getDeliveryMessage());

        List<Long> cartItemIds = orderCreateDto.getCartItemIds();

        List<CartItem> allById = cartItemRepository.findAllById(cartItemIds);
        List<OrderItem> orderItems = allById.stream().map(
                cartItem -> OrderItem.createOrderItem(cartItem.getProduct(),
                        cartItem.getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getProductSize())
        ).toList();

        Order order = Order.createOrder(member, deliveryService.findOne(deliveryId), orderItems);
        orderRepository.save(order);

        return order.getId();
    }




}
