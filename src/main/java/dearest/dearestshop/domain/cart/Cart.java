package dearest.dearestshop.domain.cart;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cart extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(Long id, Member member, List<CartItem> cartItems) {
        this.id = id;
        this.member = member;
        this.cartItems = cartItems;
    }

}
