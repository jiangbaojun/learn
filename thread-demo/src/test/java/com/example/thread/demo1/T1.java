package com.example.thread.demo1;

import org.junit.jupiter.api.Test;

public class T1 {

    boolean stop = false;

    @Test
    public void t1(){
        new Thread(() -> {
            int i = 0;
            //线程1
            while(!stop){
                System.out.println(i++);
            }
        }).start();
        new Thread(() -> {
            //线程2
            stop = true;
        }).start();
    }
}
