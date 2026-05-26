package dearest.dearestshop.domain.wishlist;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WishListItem{

    @Id @GeneratedValue
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public WishListItem(Long id, WishList wishList, Product product) {
        this.id = id;
        this.wishList = wishList;
        this.product = product;
    }
}
