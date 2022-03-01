package com.mrk.ws.ws4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/1/17 11:01
 */
//@Controller
//@RequestMapping("/ws4")
public class WebController {


    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "ws4/index";
    }

}
