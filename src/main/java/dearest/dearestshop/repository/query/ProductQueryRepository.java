package dearest.dearestshop.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dearest.dearestshop.domain.product.Product;
import dearest.dearestshop.domain.product.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;

    public List<Product> searchProducts(String keyword, Long categoryId, String sort){
        return queryFactory
                .selectFrom(product)
                .join(product.category).fetchJoin()
                .where(
                        categoryEq(categoryId),
                        keywordContains(keyword)
                )
                .orderBy(sortProducts(sort))
                .fetch();
    }

    public BooleanExpression categoryEq(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return product.category.id.eq(categoryId);
    }

    public BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return product.productName.containsIgnoreCase(keyword);
    }

    public OrderSpecifier<?> sortProducts(String sort) {
        if ("sales".equals(sort)) {
            return product.salesCount.desc();
        }

        return product.createdAt.desc();
    }


}



