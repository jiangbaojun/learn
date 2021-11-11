package com.example.thread.demo1;

import java.util.concurrent.TimeUnit;

public class Test {
    public volatile int inc = 0;

    public void t1() throws InterruptedException {
        for(int i=0;i<10;i++){
            new Thread(() -> {
                for(int j=0;j<1000;j++){
                    inc++;
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println("result: "+inc);
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();
        test.t1();
    }
}
