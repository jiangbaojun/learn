package com.tuling.part1;

//step1:继承自Thread类
class MyThread extends Thread{

    //private int ticket = 12;
    private static int ticket = 12;

    //给线程起名字
    public MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        //线程的执行体
        for (int i = 0; i < 4; i++) {
            //System.out.println("新开的线程："+i);

            System.out.println(Thread.currentThread().getName()+"卖了一张票，剩下："+ticket--);
        }
    }
}



public class Demo1 {
    public static void main(String[] args) throws InterruptedException {//程序的入口


        //step2:创建子类对象
        MyThread t1 = new MyThread("窗口1");
        MyThread t2 = new MyThread("窗口2");
        MyThread t3 = new MyThread("窗口3");
        //step3:调用start()方法
        //myThread.run();//这样写，是普通的方法的调用
        t1.start();//相当于开启一个新的线程，这个新的线程跟main线程是平级的
        t2.start();
        t3.start();

        //java.exe
//        while(true){
//
//        }

//        for (int i = 0; i < 10; i++) {
//            System.out.println("主线程:"+i);
//        }

    }
}
