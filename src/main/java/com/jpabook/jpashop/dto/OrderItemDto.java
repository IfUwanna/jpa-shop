package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.OrderItem;
import lombok.Data;

/**
 * packageName    : com.jpabook.jpashop.dto
 * fileName       : OrderItemDto
 * author         : Jihun Park
 * date           : 2022/03/29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/29        Jihun Park       최초 생성
 */
@Data
public class OrderItemDto {
    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}

