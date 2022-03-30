package com.jpabook.jpashop.service.query;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.OrderDto;
import com.jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * packageName    : com.jpabook.jpashop.service.query
 * fileName       : OrderQueryService
 * author         : Jihun Park
 * date           : 2022/03/30
 * description    : OSIV OFF 시 변환 로직을 Service안으로 이동
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/30        Jihun Park       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

}
