package com.tuling.part5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("我要一个教练");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("教练来了："+Thread.currentThread().getName());
        System.out.println("教完后，教练回到了游泳池");
    }
}


public class ThreadPoolDemo {
    public static void main(String[] args) {

        //创建线程池对象。
        //创建一个固定长度的线程池，当到达线程最大数量时，线程池的规模将不再变化。
        //ExecutorService pool = Executors.newFixedThreadPool(3);
        //创建一个可缓存的线程池，如果当前线程池的规模超出了处理需求，将回收空的线程；当需求增加时，会增加线程数量
        //ExecutorService pool = Executors.newCachedThreadPool();
        //创建一个单线程的Executor
        //ExecutorService pool = Executors.newSingleThreadExecutor();

        //创建Runnable接口子类对象。(task)
        MyRunnable myRunnable = new MyRunnable();
//        //提交Runnable接口子类对象。(take task)
//        pool.submit(myRunnable);
//        pool.submit(myRunnable);
//        pool.submit(myRunnable);
//        //注意：submit方法调用后，程序并不终止，因为线程池控制了线程的关闭
//
//
//        //关闭线程池(一般不做)。
//        pool.shutdown();


        //
        //new Thread(myRunnable,"线程1").start();


        //创建一个固定长度的线程池，而且以延迟或者定时的方式来执行，类似Timer
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 3; i++) {
            scheduledExecutorService.schedule(myRunnable,3, TimeUnit.SECONDS);//每个任务延迟10秒执行
        }

        scheduledExecutorService.shutdown();//关闭线程池,不会马上终止程序
        while(!scheduledExecutorService.isTerminated()){

        }

        System.out.println("程序终止!!");


    }
}
