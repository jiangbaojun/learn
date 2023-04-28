package com.example.thread.demo1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class Demo1 {

    /**内部线程拿到副本，修改后不会相互影响*/
    InheritableThreadLocal<String> LOCAL_VAR = new InheritableThreadLocal<>();

    @Test
    public void t1() throws InterruptedException {
        LOCAL_VAR.set("0");

        Thread l1 = new Thread(() -> {
            System.out.println("l1_before:" + LOCAL_VAR.get());
            LOCAL_VAR.set("1");
            new Thread(() -> {
                System.out.println("l2_before:" + LOCAL_VAR.get());
                LOCAL_VAR.set("2");
                System.out.println("l2_after:" + LOCAL_VAR.get());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("l2_after2s:" + LOCAL_VAR.get());
            }).start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOCAL_VAR.set("11");
            System.out.println("l1_after:" + LOCAL_VAR.get());

        });
        System.out.println("main_before:"+LOCAL_VAR.get());
        l1.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("main_after:"+LOCAL_VAR.get());
    }
}
