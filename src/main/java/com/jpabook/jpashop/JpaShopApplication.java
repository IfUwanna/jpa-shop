package com.jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaShopApplication {

    public static void main(String[] args) {

        Hello hello = new Hello();
        hello.setData("지훈");
        System.out.println("hello.getData() = " + hello.getData());

        SpringApplication.run(JpaShopApplication.class, args);

    }

}
