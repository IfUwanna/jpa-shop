package com.jpabook.jpashop.form;

import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.jpabook.jpashop.repository
 * fileName       : OrderSearch
 * author         : Jihun Park
 * date           : 2022/03/18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/18        Jihun Park       최초 생성
 */
@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태[ORDER, CANCEL]
}
