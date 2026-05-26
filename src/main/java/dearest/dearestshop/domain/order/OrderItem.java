package dearest.dearestshop.domain.order;

import dearest.dearestshop.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int quantity;

    public OrderItem(Long id,
                     Product product,
                     Order order,
                     int orderPrice,
                     int quantity) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }
}
