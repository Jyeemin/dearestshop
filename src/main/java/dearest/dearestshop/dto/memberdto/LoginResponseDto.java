package dearest.dearestshop.dto.memberdto;

import dearest.dearestshop.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String token;
    private Role role;

    public LoginResponseDto(String token){
        this.token = token;
    }


}
