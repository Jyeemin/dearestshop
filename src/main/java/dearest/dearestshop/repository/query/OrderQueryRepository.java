package dearest.dearestshop.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.domain.order.Order;
import dearest.dearestshop.domain.order.OrderItem;
import dearest.dearestshop.domain.order.OrderStatus;
import dearest.dearestshop.domain.order.QOrder;
import dearest.dearestshop.domain.product.ImageType;
import dearest.dearestshop.domain.product.ProductImage;
import dearest.dearestshop.dto.orderdto.OrderItemDto;
import dearest.dearestshop.dto.orderdto.OrderSearchCondition;
import dearest.dearestshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final MemberService memberService;
    private final QOrder order = QOrder.order;

    /**
     * 전체주문조회
     * @param condition
     * @return
     */
    public List<Order> searchOrders(OrderSearchCondition condition) {

        return queryFactory
                .selectFrom(order)
                .where(
                        memberEq(condition.getMemberId()),
                        statusEq(condition.getOrderStatus()),
                        dateGoe(condition.getStartDate()),
                        dateLoe(condition.getEndDate())
                )
                .orderBy(order.createdAt.desc())
                .fetch();

    }


    public BooleanExpression memberEq(Long memberId) {
        Member member = memberService.getLoginMember();

        if (member == null) {
            return null;
        }

        if (member.getRole().equals(Role.ADMIN)) {
            if (memberId != null) {
                return order.member.id.eq(memberId);
            }
            return null;
        }
        return order.member.id.eq(member.getId());
    }

    public BooleanExpression statusEq(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return order.orderStatus.eq(status);
    }

    public BooleanExpression dateGoe(LocalDateTime startDate) {
        if (startDate == null) {
            return null;
        }
        return order.createdAt.goe(startDate);
    }

    public BooleanExpression dateLoe(LocalDateTime endDate) {
        if (endDate == null) {
            return null;
        }
        return order.createdAt.loe(endDate);
    }


}
