package com.jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.jpabook.jpashop
 * fileName       : HelloController
 * author         : Jihun Park
 * date           : 2022/03/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/16        Jihun Park       최초 생성
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model, String name){
        model.addAttribute("data",name);
        return "hello";

    }
}
