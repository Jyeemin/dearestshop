package dearest.dearestshop.domain.order;

import dearest.dearestshop.domain.Address;
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

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //[배송상태] READY, COMP]

    //생성 메소드
    public static Delivery createDelivery(Address address){
        Delivery delivery = new Delivery();
        delivery.address = address;
        delivery.deliveryStatus = DeliveryStatus.READY;
        return delivery;
    }

    //편의 메서드
    public void addOrder(Order order) {
        this.order = order;
    }
}
