package dearest.dearestshop.domain.wishlist;

import dearest.dearestshop.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishlistItem {

    @Id @GeneratedValue
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //생성 메소드
    public static WishlistItem createWishlistItem(
            Wishlist wishlist,
            Product product
    )
    {
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.wishlist = wishlist;
        wishlistItem.product = product;
        return wishlistItem;
    }

    //편의 메서드
    public void addWishlist(Wishlist wishlist){
        this.wishlist = wishlist;
    }
}
