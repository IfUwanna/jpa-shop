package com.jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.jpabook.jpashop.service
 * fileName       : UpdateItemDto
 * author         : Jihun Park
 * date           : 2022/03/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/27        Jihun Park       최초 생성
 */
@Getter @Setter
public class UpdateItemDto {

    private Long id;
    private String name;
    private int price;

    public UpdateItemDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
