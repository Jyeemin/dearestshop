package dearest.dearestshop.repository;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByMember(Member member
    );
}
