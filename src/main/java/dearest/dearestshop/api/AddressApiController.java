package dearest.dearestshop.api;

import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.dto.addressdto.AddressResponseDto;
import dearest.dearestshop.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressApiController {

    private final AddressService addressService;

    /**
     * 주소추가
     * @param addressCreateDto
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Long>> addAddress(@RequestBody AddressCreateDto addressCreateDto) {
        Long address = addressService.createAddress(addressCreateDto);
        System.out.println("주소 추가 완료" + address);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주소 추가 성공",
                address
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 주소수정
     * @param addressResponseDto
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Long>> updateAddres(@RequestBody AddressResponseDto addressResponseDto) {
        Long address = addressService.updateAddress(addressResponseDto);
        System.out.println("주소 수정 완료" + address);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주소 수정 성공",
                address
        );
        return ResponseEntity.ok(response);
    }

    /**
     *
     * 주소 삭제
     * @param addressId
     * @return
     */
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<ApiResponse<Long>> deleteAddress(@PathVariable Long addressId) {
        Long address = addressService.deleteAddress(addressId);
        System.out.println("주소 삭제 완료" + address);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주소 삭제 성공",
                address
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 주소 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> addresses() {
        List<AddressResponseDto> addresses = addressService.addresses();

        ApiResponse<List<AddressResponseDto>> response = new ApiResponse<>(
                true,
                "주소 조회 성공",
                addresses
        );

        return ResponseEntity.ok(response);
    }

}
