package dearest.dearestshop.dto.productdto;

import dearest.dearestshop.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {
    private String productName;

    private String detailDescription;

    private int price;

    private List<ProductSize> size = new ArrayList<>();

    private List<String> urls = new ArrayList<>();
}
