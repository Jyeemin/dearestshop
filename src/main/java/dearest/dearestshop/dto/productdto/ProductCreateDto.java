package dearest.dearestshop.dto.productdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductCreateDto {
    private String productName;

    private String detailDescription;

    private int price;

    private int stockQuantity;

    private Long categoryId;

}
