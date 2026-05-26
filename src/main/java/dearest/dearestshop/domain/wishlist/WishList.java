package dearest.dearestshop.domain.wishlist;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WishList extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    public WishList(Long id, Member member) {
        this.id = id;
        this.member = member;
    }
}
