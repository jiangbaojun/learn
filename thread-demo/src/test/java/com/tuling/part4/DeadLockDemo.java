package com.tuling.part4;

public class DeadLockDemo {
    private static Object lock1 = new Object();//锁1，资源1
    private static Object lock2 = new Object();//锁2，资源2

    public static void main(String[] args) {

        //启动一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(lock1){
                    System.out.println(Thread.currentThread().getName()+"拿到了锁1，资源1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"等待锁2，资源2");
                    synchronized (lock2){
                        System.out.println(Thread.currentThread().getName()+"拿到了锁2，资源2");
                    }
                }
            }
        },"线程1").start();


        //产生死锁的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(lock2){
                    System.out.println(Thread.currentThread().getName()+"拿到了锁2，资源2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"等待锁1，资源1");
                    synchronized (lock1){
                        System.out.println(Thread.currentThread().getName()+"拿到了锁1，资源1");
                    }
                }
            }
        },"线程2").start();



    }
}
