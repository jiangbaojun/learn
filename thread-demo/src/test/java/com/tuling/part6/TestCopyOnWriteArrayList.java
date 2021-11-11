package com.tuling.part6;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        //创建一个线程池
        ExecutorService pool= Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        list.add("content"+new Random().nextInt(100));
                    }
                }
            });
        }

        //关闭线程池
        //4关闭线程池
        pool.shutdown();
        while(!pool.isTerminated()){

        }
        //5打印结果
        System.out.println("元素个数:"+list.size());
        for (String string : list) {
            System.out.println(string);
        }
    }
}
