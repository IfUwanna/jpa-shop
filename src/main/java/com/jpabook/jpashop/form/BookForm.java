package com.jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.jpabook.jpashop.controller
 * fileName       : BookForm
 * author         : Jihun Park
 * date           : 2022/03/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/27        Jihun Park       최초 생성
 */
@Getter @Setter
public class BookForm {

    private Long id;
    //item
    private String name;
    private int price;
    private int stockQuantity;
    //book
    private String author;
    private String isbn;

}
