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
public class Wishlist extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "wishlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    //연관관계 편의 메소드
    public void addWishlistItem(WishlistItem wishlistItem){
        wishlistItems.add(wishlistItem);
        wishlistItem.addWishlist(this);
    }

    //생성 메소드public interface WishlistItemRepository
    //        extends JpaRepository<WishlistItem, Long> {
    //
    //    @Query("""
    //        select w.product.id
    //        from WishlistItem w
    //        where w.wishlist.member = :member
    //    """)
    //    List<Long> findProductIdsByMember(Member member);
    //
    public static Wishlist createWishlist(
            Member member
    ) {
        Wishlist wishlist = new Wishlist();
        wishlist.member = member;
        return wishlist;
    }

}
