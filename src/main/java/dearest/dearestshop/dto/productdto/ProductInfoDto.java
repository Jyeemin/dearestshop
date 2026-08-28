package dearest.dearestshop.dto.productdto;

import dearest.dearestshop.domain.product.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductInfoDto {

    private ImageType imageType;

    private int sortOrder;

}
