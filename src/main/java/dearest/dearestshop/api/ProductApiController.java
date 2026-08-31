package dearest.dearestshop.api;

import dearest.dearestshop.dto.cartdto.CartAddDto;
import dearest.dearestshop.dto.cartdto.CartListDto;
import dearest.dearestshop.dto.productdto.ProductDetailResponseDto;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.service.CartService;
import dearest.dearestshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    private final CartService cartService;

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

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<Long>> addCart(
            @RequestBody CartAddDto cartAddDto
    ) {
        System.out.println("카트 api 실행");
        Long id = cartService.addCart(cartAddDto);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "카트 상품 추가 성공",
                id
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/cartlist")
    public ResponseEntity<ApiResponse<List<CartListDto>>> cartList() {
        List<CartListDto> cartListDtos = cartService.cartList();

        ApiResponse<List<CartListDto>> response = new ApiResponse<>(
                true,
                "카트 리스트 조회 성공",
                cartListDtos
        );
        return ResponseEntity.ok(response);
    }





}
