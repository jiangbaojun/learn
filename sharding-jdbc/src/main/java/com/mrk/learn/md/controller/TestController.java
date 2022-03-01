package com.mrk.learn.md.controller;

import com.mrk.learn.md.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

    /**
     * 其他查询
     */
    @RequestMapping("/queryOrders")
    public List<Map<String,Object>> queryOrder(HttpServletRequest request){
        return testService.queryOrder();
    }
    @RequestMapping("/addOrders")
    public int addOrders(HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("id", Long.parseLong(request.getParameter("id")));
        params.put("userid", 123);
        params.put("status", "1");
        return testService.addOrders(params);
    }

    @RequestMapping("/queryUsers")
    public List<Map<String,Object>> queryUsers(HttpServletRequest request){
        return testService.queryUsers();
    }
    @RequestMapping("/addUser")
    public int addUser(HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("id", Long.parseLong(request.getParameter("id")));
        params.put("name", "1");
        return testService.addUser(params);
    }

}
