package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.dto.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.repository
 * fileName       : OrderSimpleQueryRepository
 * author         : Jihun Park
 * date           : 2022/03/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/28        Jihun Park       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    //간단한 회원조회 v4 JPA에서 DTO로 바로 조회 예제
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new com.jpabook.jpashop.dto.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o " +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
