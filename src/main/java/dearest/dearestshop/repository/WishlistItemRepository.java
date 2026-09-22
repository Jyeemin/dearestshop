package dearest.dearestshop.repository;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.wishlist.Wishlist;
import dearest.dearestshop.domain.wishlist.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    /**
     * 조건 멤버의 상품id를 위시리스트아이템에서 찾음
     * @param member
     * @return
     */
    @Query("""
            select w.product.id
            from WishlistItem w
            where w.wishlist.member = :member
            """)
    List<Long> findProductIdsByMember(@Param("member") Member member);

    @Query("""
    select w
    from WishlistItem w
    join fetch w.product p
    join fetch p.category
    where w.wishlist.member = :member
""")
    List<WishlistItem> findAllByMember(@Param("member") Member member);

    Boolean existsByWishlistAndProduct(Wishlist wishlist, Product product);

    void deleteByWishlistAndProduct(Wishlist wishlist, Product product);





}
