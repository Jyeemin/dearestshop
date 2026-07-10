package dearest.dearestshop.domain.wishlist;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    //생성 메소드
    public static WishListItem createWishListItem(
            Product product
    )
    {
        WishListItem wishListItem = new WishListItem();
        wishListItem.product = product;
        return wishListItem;
    }

    //편의 메서드
    public void addWishList(WishList wishList){
        this.wishList = wishList;
    }
}
