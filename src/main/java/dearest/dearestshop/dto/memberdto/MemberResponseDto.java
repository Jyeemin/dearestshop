package dearest.dearestshop.dto.memberdto;


import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.dto.orderdto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Address address;

    private Role role;

}






