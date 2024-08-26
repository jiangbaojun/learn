package com.mrk.learn.atomikos.controller;

import com.mrk.learn.atomikos.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

    @RequestMapping("/t1")
    public String ttManual(HttpServletRequest request, @RequestParam Boolean error) {
        testService.performTransaction(error);
        return "ok";
    }



}
