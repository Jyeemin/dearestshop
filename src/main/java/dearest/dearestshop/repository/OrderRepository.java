package dearest.dearestshop.repository;

import dearest.dearestshop.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            select o
            from Order o
            join fetch o.delivery
            join fetch o.member
            where o.id = :orderId
            """)
    Optional<Order> findOrderDetail(@Param("orderId") Long orderId);
}
