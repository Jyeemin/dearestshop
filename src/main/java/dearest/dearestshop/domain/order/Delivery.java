package dearest.dearestshop.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private String zoneCode;

    private String roadAddress;

    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //[배송상태] READY, COMP]

    //생성 메소드
    public static Delivery createDelivery(
            String zoneCode, String roadAddress, String detailAddress){
        Delivery delivery = new Delivery();
        delivery.zoneCode = zoneCode;
        delivery.roadAddress = roadAddress;
        delivery.detailAddress = detailAddress;
        delivery.deliveryStatus = DeliveryStatus.READY;
        return delivery;
    }

    //편의 메서드
    public void addOrder(Order order) {
        this.order = order;
    }
}
