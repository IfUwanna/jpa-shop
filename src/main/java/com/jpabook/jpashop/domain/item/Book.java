package com.jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName    : com.jpabook.jpashop.domain.item
 * fileName       : Book
 * author         : Jihun Park
 * date           : 2022/03/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/16        Jihun Park       최초 생성
 */
@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
