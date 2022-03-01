package com.mrk.ws.ws1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/1/17 11:01
 */
@Controller
@RequestMapping("/ws1")
public class Web1Controller {


    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        request.setAttribute("userId", "xiaoming");
        return "ws1/ws1";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        String msg = request.getParameter("msg");
        Web1Service.sendMsg(id,msg);
        return msg;
    }
}
