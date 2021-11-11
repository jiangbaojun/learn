package com.tuling.part3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyList{
    private Lock lock = new ReentrantLock();
    private String[] strArr = {"A","B","","",""};
    private int index = 2;

    public  void add(String str){
        lock.lock();//手动加锁

        try {
            strArr[index] = str;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            index++;
            System.out.println(Thread.currentThread().getName()+"添加了"+str);

        } finally {
            lock.unlock();//释放锁,保证锁的释放，
        }
    }

    public String[] getStrArr(){
        return strArr;
    }

}

//class MyRunnable implements Runnable{
//
//    @Override
//    public void run() {
//
//    }
//}

public class TestMyList {
    public static void main(String[] args) {
        MyList myList = new MyList();

        //将实现类的对象赋值给接口
        //Runnable myRunnable = new MyRunnable();
        //匿名内部类
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                myList.add("hello");
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                myList.add("world");
            }
        };
        Thread t1 = new Thread(r1,"线程A");
        Thread t2 = new Thread(r2,"线程B");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] strArr = myList.getStrArr();
        for (String str : strArr) {
            System.out.println("str:"+str);
        }





    }
}
