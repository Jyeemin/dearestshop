package dearest.dearestshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

//값 타입은 변경 불가능하게 설계해야한다
@Embeddable
@Getter
public class Address {

    private String baseaddress;
    private String detailaddress;

    public Address(String baseaddress, String detailaddress) {
        this.baseaddress = baseaddress;
        this.detailaddress = detailaddress;
    }
}

