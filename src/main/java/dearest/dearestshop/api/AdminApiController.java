package dearest.dearestshop.api;

import dearest.dearestshop.dto.memberdto.MemberResponseDto;
import dearest.dearestshop.dto.productdto.ProductCreateDto;
import dearest.dearestshop.dto.productdto.ProductInfoDto;
import dearest.dearestshop.service.MemberService;
import dearest.dearestshop.service.OrderService;
import dearest.dearestshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * 회원목록조회
     */
    @GetMapping("/members")
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> members(){
        List<MemberResponseDto> members = memberService.findAll();
        ApiResponse<List<MemberResponseDto>> response = new ApiResponse<>(
                true,
                "회원목록 조회",
                members
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 등록
     * @param productCreateDto
     * @param images
     * @param productInfoDtos
     * @return
     */
    @PostMapping("/product/new")
    public ResponseEntity<ApiResponse<Long>> createProduct(@RequestPart("product") ProductCreateDto productCreateDto,
                                                             @RequestPart("images") List<MultipartFile> images,
                                                             @RequestPart("productInfoDtos") List<ProductInfoDto> productInfoDtos) {
        Long product = productService.createProduct(productCreateDto, images, productInfoDtos);
        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "상품 추가 성공",
                product
        );
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/order/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Long>> cancelOrder(@PathVariable Long orderId){
        Long cancelId = orderService.cancelOrder(orderId);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주문 캔슬 성공",
                cancelId
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/order/{orderId}/complete")
    public ResponseEntity<ApiResponse<Long>> completeOrder(@PathVariable Long orderId) {
        Long completeId = orderService.completeOrder(orderId);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주문 완료 성공",
                completeId
        );
        return ResponseEntity.ok(response);
    }
}
