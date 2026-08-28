package dearest.dearestshop.api;

import dearest.dearestshop.dto.memberdto.MemberResponseDto;
import dearest.dearestshop.dto.productdto.ProductCreateDto;
import dearest.dearestshop.dto.productdto.ProductInfoDto;
import dearest.dearestshop.service.MemberService;
import dearest.dearestshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class adminApiController {

    private final MemberService memberService;
    private final ProductService productService;

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
}
