package dearest.dearestshop.dto.productdto;

import dearest.dearestshop.domain.product.Category;
import dearest.dearestshop.domain.product.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;

    private String productName;

    private int price;

    private String thumbnail;

    private String categoryName;

}
