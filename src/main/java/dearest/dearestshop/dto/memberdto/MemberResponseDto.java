package dearest.dearestshop.dto.memberdto;


import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.dto.addressdto.AddressResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    List<AddressResponseDto> addresses = new ArrayList<>();

    private Role role;

}






