package com.mrk.qr.controller.t1;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * 方法上添加@Async注解，会在独立的线程中执行方法体。
 * 注意需要在启动类上添加@EnableAsync注解
 */
@Component
public class MyTask {
    private static Random random = new Random();

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }
    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }
    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }

    private long currentTimeMillis() {
        return new Date().getTime();
    }

    @Async
    public Future<String> doTaskFour() throws Exception {
        System.out.println("开始任务四");
        Thread.sleep(8000);
        System.out.println("完成任务四");
        return new AsyncResult<>("我是doTaskFour返回");
    }
}
