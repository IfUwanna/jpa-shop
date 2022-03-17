package com.jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName    : com.jpabook.jpashop.domain.item
 * fileName       : Album
 * author         : Jihun Park
 * date           : 2022/03/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/16        Jihun Park       최초 생성
 */
@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
