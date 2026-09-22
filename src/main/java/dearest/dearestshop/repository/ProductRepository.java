package dearest.dearestshop.repository;

import dearest.dearestshop.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            select p
            from Product p
            join fetch p.category
            where lower(p.productName) like lower(concat('%', :keyword, '%'))
            """)
    List<Product> searchByProductName(@Param("keyword") String keyword);

    @Query("""
            select p
            from Product p
            join fetch p.category
            """)
    List<Product> findAllWithCategory();


}
