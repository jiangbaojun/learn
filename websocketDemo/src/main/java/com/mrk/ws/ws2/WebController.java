package com.mrk.ws.ws2;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/1/17 11:01
 */
//@Controller
//@RequestMapping("/ws2")
public class WebController {


    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "ws2/index";
    }

}
