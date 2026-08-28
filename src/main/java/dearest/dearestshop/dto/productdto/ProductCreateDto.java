package dearest.dearestshop.dto.productdto;

import dearest.dearestshop.domain.product.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCreateDto {
    private String productName;

    private String detailDescription;

    private int price;

    private int stockQuantity;

    private List<ProductSize> sizes = new ArrayList<>();

    private Long categoryId;

}
