package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.SimpleOrderDto;
import com.jpabook.jpashop.dto.OrderSimpleQueryDto;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.form.OrderSearch;
import com.jpabook.jpashop.repository.query.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.jpabook.jpashop.api
 * fileName       : OrderSimpleApiController
 * author         : Jihun Park
 * date           : 2022/03/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/28        Jihun Park       최초 생성
 */

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) { //Lazy 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        //Order 2개
        //N+1문제 : 1+회원N+배송N  (N이 2개면 5회)
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        //처음 돌때 member
        List<SimpleOrderDto> result =
                orders.stream()
                        .map(o -> new SimpleOrderDto(o))
                        .collect(Collectors.toList());
        return result;
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * V4. JPA에서 DTO로 바로 조회
     * - 쿼리1번 호출
     * - select 절에서 원하는 데이터만 선택해서 조회
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


}
