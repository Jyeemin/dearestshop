package dearest.dearestshop.dto.addressdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private Long addressId;
    private String zoneCode;
    private String roadAddress;
    private String detailAddress;
    private boolean isDefault;
}
