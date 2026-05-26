package dearest.dearestshop.domain.cart;

import dearest.dearestshop.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CartItem {

    @Id @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    private int quantity;

    private Boolean isChecked;

    public CartItem(Long id,
                    Cart cart,
                    Product product,
                    int quantity,
                    Boolean isChecked) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }
}
