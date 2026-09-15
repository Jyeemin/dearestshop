package dearest.dearestshop.api;

import dearest.dearestshop.dto.cartdto.CartAddDto;
import dearest.dearestshop.dto.cartdto.CartListDto;
import dearest.dearestshop.dto.productdto.ProductDetailResponseDto;
import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.service.CartService;
import dearest.dearestshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    private final CartService cartService;

    /**
     * 상품 전체 화면 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> products(
            @RequestParam(required = false) String keyword
    ){
        List<ProductResponseDto> products;
        if (keyword == null || keyword.isBlank()) {
            products = productService.findAll();
        } else {
            products = productService.search(keyword);
        }


        ApiResponse<List<ProductResponseDto>> response = new ApiResponse<>(
                true,
                "상품조회 성공",
                products
        );
        return ResponseEntity.ok(response);
    }


    /**
     * 상품 상세 화면 조회
     * @param id
     * @return
     */
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

    /**
     * 카트에 상품 추가
     * @param cartAddDto
     * @return
     */
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

    /**
     * 카트 목록 화면 조회
     * @return
     */
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

    /**
     * 카트 아이템 수량 변경
     * @param cartItemId
     * @param quantity
     * @return
     */
    @PatchMapping("/cart/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody int quantity
    ) {
        cartService.updateQuantity(cartItemId, quantity);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "상품 수량 변경 성공",
                "ok"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> deleteCartItem(
            @PathVariable Long cartItemId
    ){
        cartService.deleteCartItem(cartItemId);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "상품 삭제 성공",
                "ok"
        );

        return ResponseEntity.ok(response);
    }


}
