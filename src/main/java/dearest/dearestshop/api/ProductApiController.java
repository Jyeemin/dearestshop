package dearest.dearestshop.api;

import dearest.dearestshop.dto.productdto.ProductDetailResponseDto;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> products(){
        List<ProductResponseDto> all = productService.findAll();

        ApiResponse<List<ProductResponseDto>> response = new ApiResponse<>(
                true,
                "상품조회 성공",
                all
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponseDto>> productDetail(
            @PathVariable Long id
    ){
        System.out.println("===== 상품 상세 Controller 실행 =====");
        System.out.println("id = " + id);
        ProductDetailResponseDto dto = productService.findOne(id);
        ApiResponse<ProductDetailResponseDto> response = new ApiResponse<>(
                true,
                "상품 상세 조회 성공",
                dto
        );
        return ResponseEntity.ok(response);
    }





}
