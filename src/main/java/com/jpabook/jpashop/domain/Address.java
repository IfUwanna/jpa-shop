package com.jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * packageName    : com.jpabook.jpashop.domain
 * fileName       : Address
 * author         : Jihun Park
 * date           : 2022/03/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/16        Jihun Park       최초 생성
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
