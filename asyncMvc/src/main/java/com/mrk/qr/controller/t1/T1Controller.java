package com.mrk.qr.controller.t1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class T1Controller {

    @Autowired
    private MyTask myTask;

    @RequestMapping("/t1")
    public String t1() throws Exception {
        myTask.doTaskOne();
        myTask.doTaskTwo();
        myTask.doTaskThree();
        System.out.println("异步任务触发完成！");
        return "success t1";
    }

    @RequestMapping("/t11")
    public String t11() throws Exception {
        System.out.println("主线程开始");
        Future<String> futureResult = myTask.doTaskFour();
        String result = null;
        try {
            //阻塞
            result = futureResult.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return "error t11："+e.getMessage();
        }
        System.out.println(result);
        System.out.println("主线程结束");
        return "success t11";
    }
}
