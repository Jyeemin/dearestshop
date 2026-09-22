package dearest.dearestshop.dto.memberdto;

import dearest.dearestshop.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private Role role;
    private String memberName;




}
