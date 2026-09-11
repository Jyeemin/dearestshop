package dearest.dearestshop.dto.addressdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDto {
    private String zoneCode;
    private String roadAddress;
    private String detailAddress;
    @JsonProperty("isDefault")
    private boolean isDefault;
}
