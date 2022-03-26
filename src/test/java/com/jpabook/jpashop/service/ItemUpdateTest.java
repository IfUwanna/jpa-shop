package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

/**
 * packageName    : com.jpabook.jpashop.service
 * fileName       : ItemUpdateTest
 * author         : Jihun Park
 * date           : 2022/03/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/27        Jihun Park       최초 생성
 */

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void test() throws Exception{

        Book book = em.find(Book.class,1L);


        //when

        //then
        //Assertions.assertThat(true).isEqualTo(true);

    }

}
