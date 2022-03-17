package com.jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName    : com.jpabook.jpashop.domain.item
 * fileName       : Moive
 * author         : Jihun Park
 * date           : 2022/03/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/16        Jihun Park       최초 생성
 */
@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Moive extends Item {

    private String director;
    private String actor;
}
