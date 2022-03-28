package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.OrderSimpleQueryDto;
import com.querydsl.core.Query;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.repository
 * fileName       : OrderRepository
 * author         : Jihun Park
 * date           : 2022/03/18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/18        Jihun Park       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        JPAQuery<Order> query = new JPAQuery<>(em);

        QOrder order = QOrder.order;
        QMember member = QMember.member;
        return query
                .select(order)
                .from(order)
                .join(order.member,member)
                .where(statusEq(orderSearch.getOrderStatus())
                        ,nameLike(orderSearch.getMemberName())
                )
                .limit(1000)
                .fetch();
    }

    private BooleanExpression statusEq(OrderStatus statusCond){
        QOrder order = QOrder.order;
        if(statusCond == null) return null;
        return order.status.eq(statusCond);
    }
    private BooleanExpression nameLike(String nameCond){
        QMember member = QMember.member;
        if(!StringUtils.hasText(nameCond)) return null;
        return member.name.like(nameCond);
    }


    @Deprecated //JPA Criteria
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" +
                    orderSearch.getMemberName() + "%");
            criteria.add(name);

        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

    @Deprecated  //JPQL로 처리
    public List<Order> findAllByString(OrderSearch orderSearch) { //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    //간단한 회원조회V3 fetch join 예제
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o " +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    // 회원조회V3 fetch join 예제
    public List<Order> findAllWithItem() {
        return em.createQuery(
        "select distinct o from Order o " +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +  // 1:N 페치조인!
                " join fetch oi.item i", Order.class
        ).setFirstResult(0).setMaxResults(100)   // WARN!!! firstResult/maxResults specified with collection fetch; applying in memory!
        .getResultList();
    }
    // 회원조회V3.1 fetch join 예제
    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
        "select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }




}
