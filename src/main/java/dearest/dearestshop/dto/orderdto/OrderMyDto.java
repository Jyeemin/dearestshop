package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMyDto {
    private Long orderId;
    private int totalPrice;
    private OrderStatus status;

}
