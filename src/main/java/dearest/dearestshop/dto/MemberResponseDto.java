package dearest.dearestshop.dto;


import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.domain.order.Order;

import java.util.List;

public class MemberResponseDto {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Address address;

    private Role role;

    private List<OrderResponseDto> orders;
}






