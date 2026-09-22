package dearest.dearestshop.dto.memberdto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginDto {
    private String email;
    private String password;
}
