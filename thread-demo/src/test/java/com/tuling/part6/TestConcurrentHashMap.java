package com.tuling.part6;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        map.put(Thread.currentThread().getName()+"--->"+j,j+"");
                        System.out.println(map);
                    }
                }
            }).start();;
        }
    }
}
