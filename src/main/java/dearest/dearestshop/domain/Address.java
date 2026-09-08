package dearest.dearestshop.domain;

import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long addressId;

    private String zoneCode;

    private String roadAddress;

    private String detailAddress;

    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //편의 메서드
    public void addMember(Member member){
        this.member = member;
    }

    public static Address createAddress(
            String zoneCode, String roadAddress, String detailAddress,
            boolean isDefault, Member member
    ) {
        Address address = new Address();
        address.zoneCode = zoneCode;
        address.roadAddress = roadAddress;
        address.detailAddress = detailAddress;
        address.isDefault = isDefault;
        address.member = member;
        return address;
    }

    public void changeDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void changeZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public void changeRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void changeDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}

