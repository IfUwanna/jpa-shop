package com.jpabook.jpashop.repository.query;

import com.jpabook.jpashop.dto.OrderFlatDto;
import com.jpabook.jpashop.dto.OrderItemQueryDto;
import com.jpabook.jpashop.dto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회 (루트 조회)
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


    /**
     * V5: JPA에서 DTO 직접 조회 - 컬렉션 조회 최적화
     * Query: 루트 1번, 컬렉션 1번
     * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식
     */
    public List<OrderQueryDto> findAllByDto_optimization() { //루트 조회(toOne 코드를 모두 한번에 조회)
        // 1. 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders();

        // 2. orderItem 컬렉션을 MAP한방에 조회 (in절 사용)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        // 3. 루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    /**
     * id List 추출
     */
    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
            .map(o -> o.getOrderId())
            .collect(Collectors.toList());
    }

    /**
     * orderItem 컬렉션 조회하여 MAP으로 반환
     */
    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long>
                                                                        orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.jpabook.jpashop.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds",
                OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    /** V6. JPA에서 DTO로 직접 조회, 플랫 데이터 최적화 */
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new com.jpabook.jpashop.dto.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }


}
