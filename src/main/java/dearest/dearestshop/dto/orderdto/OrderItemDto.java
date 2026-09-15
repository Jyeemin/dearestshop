package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long orderItemId;

    private Long productId;

    private String productName;

    private String thumbnail;

    private ProductSize size;

    private int price;

    private int quantity;
}
