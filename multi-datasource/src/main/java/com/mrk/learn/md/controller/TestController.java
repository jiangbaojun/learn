package com.mrk.learn.md.controller;

import com.mrk.learn.md.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

    /**
     * 方案二:动态数据源
     * 手动切换
     */
    @RequestMapping("/tt")
    public List<Map<String, Object>> ttManual(HttpServletRequest request) {
        return testService.selectManual(request.getParameter("db"));
    }

    /**
     * 方案二:动态数据源
     * 注解切换，在mapper层使用注解指定数据源
     */
    @RequestMapping("/tt/t")
    public List<Map<String, Object>> tt_t(HttpServletRequest request) {
        return testService.tt_t(request.getParameter("db"));
    }

    /**
     * 方案二:动态数据源
     * 注解切换，数据源1
     */
    @RequestMapping("/tt/1")
    public List<Map<String, Object>> tt1(HttpServletRequest request) {
        return testService.tta1(request.getParameter("db"));
    }
    /**
     * 方案二:动态数据源
     * 注解切换，数据源2
     */
    @RequestMapping("/tt/2")
    public List<Map<String, Object>> tt2(HttpServletRequest request) {
        return testService.tta2(request.getParameter("db"));
    }
    /**
     * 方案二:动态数据源
     * 注解切换，数据源3
     */
    @RequestMapping("/tt/3")
    public List<Map<String, Object>> tt3(HttpServletRequest request) {
        return testService.tta3(request.getParameter("db"));
    }

    /**
     * 方案一：查询数据库1
     */
    @RequestMapping("/t1")
    public List<Map<String, Object>> select1() {
        return testService.select1();
    }
    /**
     * 方案一：查询数据库2
     */
    @RequestMapping("/t2")
    public List<Map<String, Object>> select2() {
        return testService.select2();
    }
    /**
     * 方案一：查询数据库3
     */
    @RequestMapping("/t3")
    public List<Map<String, Object>> select3() {
        return testService.select3();
    }

}
