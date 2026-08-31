package dearest.dearestshop.repository;

import dearest.dearestshop.domain.cart.Cart;
import dearest.dearestshop.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember(Member member);
}
