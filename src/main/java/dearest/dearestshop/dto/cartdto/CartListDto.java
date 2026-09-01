package dearest.dearestshop.dto.cartdto;

import dearest.dearestshop.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartListDto {
    private Long cartItemId;

    private Long productId;

    private String productName;

    private int price;

    private String imgUrl;

    private ProductSize productSize;

    private int quantity;
}
