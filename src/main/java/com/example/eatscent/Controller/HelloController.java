package com.example.eatscent.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/helloo")
    public String hello() {
        System.out.println(6666);
        return "hello";
    }
}
