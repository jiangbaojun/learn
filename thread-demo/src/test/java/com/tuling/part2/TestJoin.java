package com.tuling.part2;


class JoinThread extends Thread{
    public JoinThread(String name){
        super(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"打印---->"+i);
        }
    }
}

public class TestJoin {
    public static void main(String[] args) {
        System.out.println("主线程开始执行.....");

        JoinThread joinThread = new JoinThread("新加入的线程");
        joinThread.start();
//        try {
//            joinThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("主线程结束执行.....");
    }
}
