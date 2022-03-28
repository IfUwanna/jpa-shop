package com.jpabook.jpashop.repository.query;

import com.jpabook.jpashop.dto.OrderItemQueryDto;
import com.jpabook.jpashop.dto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.repository.query
 * fileName       : OrderQueryRepository
 * author         : Jihun Park
 * date           : 2022/03/29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/29        Jihun Park       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos() {
        // 1. 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders();

        // 2. 루프를 돌면서 컬렉션 추가(추가 쿼리 실행)
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    /**
     * 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회
     */
    public List<OrderQueryDto> findOrders() {
        return em.createQuery(
        "select new com.jpabook.jpashop.dto.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                "from Order o" +
                " join o.member m " +
                " join o.delivery d", OrderQueryDto.class)
        .getResultList();
    }

    /**
     * 1:N 관계인 orderItems 조회
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new com.jpabook.jpashop.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = : orderId",
                OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

}
