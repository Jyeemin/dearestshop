package dearest.dearestshop.api;

import dearest.dearestshop.dto.productdto.ProductResponseDto;
import dearest.dearestshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> updateWishlist(
            @PathVariable Long productId
    ) {
        wishlistService.toggleWishlist(productId);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "위시리스트 업데이트 성공",
                "ok"
        );

        return ResponseEntity.ok(response);
    }

    //@DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deletewishlist(
            @PathVariable Long productId
    ) {
        wishlistService.toggleWishlist(productId);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "위시리스트 업데이트 성공",
                "ok"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 위시리스트 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> wishlists() {
        List<ProductResponseDto> myWishlist = wishlistService.findMyWishlist();

        ApiResponse<List<ProductResponseDto>> response = new ApiResponse<>(
                true,
                "위시리스트 조회 성공",
                myWishlist
        );
        return ResponseEntity.ok(response);
    }

}
