package dearest.dearestshop.repository;

import dearest.dearestshop.domain.cart.Cart;
import dearest.dearestshop.domain.cart.CartItem;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProductAndProductSize(
            Cart cart,
            Product product,
            ProductSize productSize
    );

    @Query("""
            select ci
            from CartItem ci
            join fetch ci.product
            where ci.cart.id = :cartId
            """)
    List<CartItem> findAllWithCartAndProduct(@Param("cartId") Long cartId);

}
