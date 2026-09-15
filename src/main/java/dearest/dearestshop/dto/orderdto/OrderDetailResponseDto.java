package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.domain.order.OrderStatus;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDto {
    private String memberName;
    private String email;
    private Long orderId;
    private List<OrderItemDto> orderItemDtos;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private int totalPrice;

    private String receiverName;
    private AddressCreateDto newAddress;
    private String deliveryMessage;
}
