package com.mrk.qr.controller.t3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class T3Controller {

    /**
     * AsyncContext是servlet3的特性
     * 只有手动调用了asyncContext.complete()表示异步任务执行完成，触发onComplete的监听。如果没有手动调用，会一直等待到超时，触发onTimeout监听
     * 如果任务执行超时，会触发onTimeout，但是任务不会立即终止，仍然继续执行
     */
    @RequestMapping(value = "/t3")
    public String servletReq (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("外部线程开始");
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做一些超时后的相关操作...
            }
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误："+event.getThrowable());
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成1");
                //这里可以做一些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(3000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println("内部线程1：" + Thread.currentThread().getName());
//                    System.out.println(111);
//                    asyncContext.getResponse().setCharacterEncoding("utf-8");
//                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
//                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                    System.out.println(22222232);
//                    asyncContext.complete();
                } catch (Exception e) {
                    System.out.println("异常："+e.getMessage());
                }finally {
                    //异步请求完成通知
                    //此时整个请求才完成
//                    asyncContext.complete();
                }
            }
        });
        System.out.println("外部线程结束");
        return "success";
    }
    @RequestMapping(value = "/t33")
    public void servletReq3 (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("外部线程开始");
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做一些超时后的相关操作...
            }
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误："+event.getThrowable());
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成1");
                //这里可以做一些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(3000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println("内部线程1：" + Thread.currentThread().getName());
                    System.out.println(111);
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                    System.out.println(2222);
                } catch (Exception e) {
                    System.out.println("异常："+e.getMessage());
                }finally {
                    //异步请求完成通知
                    //此时整个请求才完成
                    asyncContext.complete();
                }
            }
        });
        System.out.println("外部线程结束");
    }

}
