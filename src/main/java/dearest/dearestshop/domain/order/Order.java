package dearest.dearestshop.domain.order;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태 [ORDER,CANCEL]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public Order(Long id,
                 Member member,
                 List<OrderItem> orderItems,
                 OrderStatus orderStatus,
                 Delivery delivery) {
        this.id = id;
        this.member = member;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.delivery = delivery;
    }

}
