package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.domain.order.OrderStatus;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderResponseDto {
    private Long orderId;
    private List<ProductResponseDto> products;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private int totalPrice;
}
