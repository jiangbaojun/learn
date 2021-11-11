package com.tuling.part1;

//step1:实现Runnable接口
class MyRunnable  implements Runnable{

    private int ticket = 12;

    @Override
    public void run() {
        //线程的执行体
        //线程的执行体
        for (int i = 0; i < 4; i++) {
            //System.out.println("新开的线程："+i);
            System.out.println(Thread.currentThread().getName()+"卖了一张票，剩下："+ticket--);
        }
    }
}

public class Demo2 {
    public static void main(String[] args) {

        //step2:创建实现类的对象
        MyRunnable myRunnable = new MyRunnable();
        Thread t1 = new Thread(myRunnable,"窗口1");
        Thread t2 = new Thread(myRunnable,"窗口2");
        Thread t3 = new Thread(myRunnable,"窗口3");
        //step3:启动一个线程
        t1.start();
        t2.start();
        t3.start();


//        System.out.println("----------------------");
//
//        //主线程
//        for (int i = 0; i < 10; i++) {
//            System.out.println("主线程:"+i);
//        }


    }
}
