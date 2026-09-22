package dearest.dearestshop.domain.member;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @OneToMany(mappedBy = "member",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
   private List<Order> orders = new ArrayList<>();

    //연관관계 편의 메서드 추가 member-order
    public void addOrder(Order order){
        order.addMember(this);
        orders.add(order);
    }

    public void addAddress(Address address) {
        address.addMember(this);
        addresses.add(address);
    }

    //생성 메소드
    public static Member createMember(
            String name,
            String email,
            String password,
            String phoneNumber
    ) {
        Member member = new Member();

        member.name = name;
        member.email = email;
        member.password = password;
        member.phoneNumber = phoneNumber;

        member.role = Role.USER;

        return member;
    }

    //편의 메서드

    //비밀번호 변경
    public void changePassword(String password) {
        this.password = password;
    }

    //이메일 변경
    public void changeEmail(String email) {
        this.email = email;
    }

    //번호 변경
    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeRole(Role role) {this.role = role;
    }


}
