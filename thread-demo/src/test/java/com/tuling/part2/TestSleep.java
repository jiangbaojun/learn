package com.tuling.part2;

class SleepThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("新开的线程："+i);
        }
    }
}

public class TestSleep {
    public static void main(String[] args) {

        //
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();;
        System.out.println("---------------------");
        //让当前线程睡眠10秒
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 10; i++) {
            System.out.println("主线程："+i);
        }


    }
}
