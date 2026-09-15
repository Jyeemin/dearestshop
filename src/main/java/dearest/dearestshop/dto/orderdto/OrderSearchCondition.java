package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.domain.order.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderSearchCondition {
    private Long memberId;

    private OrderStatus orderStatus;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
