package dearest.dearestshop.dto;

import dearest.dearestshop.domain.order.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private int totalPrice;
}
