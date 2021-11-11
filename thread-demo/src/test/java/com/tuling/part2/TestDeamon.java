package com.tuling.part2;

class DeamonThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("守护线程打印："+i);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class TestDeamon {
    public static void main(String[] args) {
        //启动一个守护线程
        DeamonThread deamonThread = new DeamonThread();
        deamonThread.setDaemon(true);//就说明我这个是守护线程
        deamonThread.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("主线程打印："+i);
        }


    }
}
