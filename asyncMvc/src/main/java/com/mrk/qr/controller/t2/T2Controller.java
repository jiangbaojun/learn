package com.mrk.qr.controller.t2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class T2Controller {

    /**
     * 主线程执行完成后，通过return调起WebAsyncTask中指定的callable任务。然后释放web线程（主线程），用于处理其他web请求。
     * 当WebAsyncTask执行完成后，通过response返回结果
     * https://juejin.cn/post/6844903879864385549
     */
    @RequestMapping(value = "/t2/1")
    public WebAsyncTask<String> webAsyncReq () throws InterruptedException {
        System.out.println("外部线程开始");
        Callable<String> result = () -> {
            System.out.println("内部线程开始：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("内部线程结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("内部线程返回：" + Thread.currentThread().getName());
            return "success";
        };
        WebAsyncTask<String> wat = new WebAsyncTask<String>(3000L, result);
        wat.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "超时";
            }
        });
        System.out.println("外部线程结束");
        return wat;
    }

    /**
     * DeferredResult可以处理一些相对复杂一些的业务逻辑，最主要还是可以在另一个线程里面进行业务处理及返回，即可在两个完全不相干的线程间的通信。
     * @return
     */
    @RequestMapping(value = "/t2/2")
    public DeferredResult<String> deferredResultReq () {
        System.out.println("外部线程开始");
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60*1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("DeferredResult超时");
                result.setResult("超时了!");
            }
        });
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                //完成后
                System.out.println("调用完成");
            }
        });
        Executors.newFixedThreadPool(2).execute(new Runnable() {
            @Override
            public void run(){
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("内部线程结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //处理业务逻辑
                System.out.println("内部线程：" + Thread.currentThread().getName());
                //返回结果
                result.setResult("DeferredResult!!");
            }
        });
        System.out.println("外部线程结束");
        return result;
    }
}
