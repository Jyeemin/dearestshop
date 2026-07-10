package dearest.dearestshop.domain.wishlist;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishList extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "wishList",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WishListItem> wishListItems = new ArrayList<>();

    //연관관계 편의 메소드
    public void addWishListItem(WishListItem wishListItem){
        wishListItems.add(wishListItem);
        wishListItem.addWishList(this);
    }

    //생성 메소드
    public static WishList createWishList(
            Member member
    ) {
        WishList wishList = new WishList();
        wishList.member = member;
        return wishList;
    }

}
