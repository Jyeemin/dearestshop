package dearest.dearestshop.dto.cartdto;

import dearest.dearestshop.domain.product.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartAddDto {
    private Long productId;

    private ProductSize size;

    private int quantity;


}
