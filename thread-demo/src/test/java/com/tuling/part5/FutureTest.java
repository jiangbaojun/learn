package com.tuling.part5;

import java.util.concurrent.*;

class HandleCallable implements Callable<Integer>{

    private String name;
    public HandleCallable(String name){
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(this.name+"开始计算了...");
        //睡眠
        Thread.sleep(200);

        int sum = 0;
        //产生一个随机数
        int random = (int)(Math.random()*10);//Math.random()--->0~1
        System.out.println(this.name+"产生的随机数："+random);
        for (int i = 0; i <= random; i++) {
            sum  +=i;
        }
        return sum;
    }
}

public class FutureTest {
    public static void main(String[] args) {

        //线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);
        HandleCallable t1 = new HandleCallable("线程1");
        HandleCallable t2 = new HandleCallable("线程2");
        HandleCallable t3 = new HandleCallable("线程3");
        //提交任务
        Future<Integer> result1 = pool.submit(t1);
        Future<Integer> result2 = pool.submit(t2);
        Future<Integer> result3 = pool.submit(t3);

        //关闭线程池
        pool.shutdown();

        //将结果打印一下
        try {
            System.out.println("线程1的结果："+result1.get());
            System.out.println("线程2的结果："+result2.get());
            System.out.println("线程3的结果："+result3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
