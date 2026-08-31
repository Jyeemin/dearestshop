package dearest.dearestshop.domain.cart;

import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductSize;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private ProductSize productSize;

    //생성 메소드
    //생성 메소드
    public static CartItem createCartItem(
            Product product,
            int quantity,
            ProductSize productSize
    ){
        CartItem cartItem = new CartItem();
        cartItem.product = product;
        cartItem.quantity = quantity;
        cartItem.productSize = productSize;
        return cartItem;
    }

    //편의 메서드
    public void addCart(Cart cart){
        this.cart = cart;
    }
}
