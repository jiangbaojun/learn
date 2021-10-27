package com.mrk.qr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }
    @RequestMapping("/jsqr")
    public String jsqr(HttpServletRequest request, HttpServletResponse response){
        return "jsqr";
    }
}
