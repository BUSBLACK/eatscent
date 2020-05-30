package com.example.eatscent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/helloo")
    public String hello() {
        System.out.println(6666);
        return "hello";
    }
}
