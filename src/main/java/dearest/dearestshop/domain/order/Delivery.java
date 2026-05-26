package dearest.dearestshop.domain.order;

import dearest.dearestshop.domain.Address;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //[배송상태] READY, COMP]

    public Delivery(Long id, Order order, Address address, DeliveryStatus status) {
        this.id = id;
        this.order = order;
        this.address = address;
        this.status = status;
    }
}
