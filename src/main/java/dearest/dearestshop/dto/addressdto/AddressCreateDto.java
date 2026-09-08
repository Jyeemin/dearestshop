package dearest.dearestshop.dto.addressdto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressCreateDto {
    private String zoneCode;
    private String roadAddress;
    private String detailAddress;
    private boolean isDefault;
}
