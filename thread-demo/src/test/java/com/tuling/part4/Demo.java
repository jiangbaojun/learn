package com.tuling.part4;

//顾客线程跟老板说，我要包子，馒头
//老板线程给顾客相应的东西


public class Demo {
    public static void main(String[] args) {
        Object lock = new Object();//这个就是协调者的角色，

        new Thread(new Runnable() {
            @Override
            public void run() {

                //进入等待
                synchronized (lock){
                    System.out.println("顾客1线程：1、告诉老板要的早餐种类和数量....");
                    try {
                        lock.wait();
                        //lock.wait(4000);//会等待指定的时间，如果没有被唤醒，那么会自己唤醒，执行后面的代码
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("顾客1线程：3、拿到老板给的早餐，开始吃....");
                }

            }
        },"顾客线程1").start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                //进入等待
                synchronized (lock){
                    System.out.println("顾客2线程：1、告诉老板要的早餐种类和数量....");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("顾客2线程：3、拿到老板给的早餐，开始吃....");
                }

            }
        },"顾客线程2").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //等待2秒时间
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //通知顾客
                synchronized (lock){
                    System.out.println("2、老板做好了，交给顾客.....");
                    //lock.notify();//通知
                    lock.notifyAll();//通知所有等待的线程
                }
            }
        },"老板线程").start();


    }
}
