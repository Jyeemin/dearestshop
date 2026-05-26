package dearest.dearestshop.domain.member;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.order.Order;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private LocalDate birthDate;

    private String phoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
   private List<Order> orders = new ArrayList<>();

    //생성자
    protected Member(
            String name,
            String email,
            String password,
            LocalDate birthDate,
            String phoneNumber,
            Address address
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Member createMember(
            String name,
            String email,
            String password,
            LocalDate birthDate,
            String phoneNumber,
            Address address
    ) {
        return new Member(
                name,
                email,
                password,
                birthDate,
                phoneNumber,
                address
        );
    }

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

    //주소 변경
    public void changeAddress(Address address) {
        this.address = address;
    }


}
