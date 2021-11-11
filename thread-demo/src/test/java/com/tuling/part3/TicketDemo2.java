package com.tuling.part3;

//class Person{}

class Ticket2 implements Runnable{
    private static int ticket = 100;
    Object lock =new Object();
//    Person p = new Person();
    @Override
    public void run() {
        while(true){//只要有票就一直卖

            /*
            synchronized(临界资源对象){ //对临界资源对象加锁
            //代码（原子操作）
            }
             */
            //synchronized (lock){//每次只会允许一个线程进来执行里面的代码
            //synchronized (this){
            synchronized (Ticket2.class){//.class文件加载到内存的时候，只会创建一个Class类的对象
                if(ticket>0){
                    //模拟一下出票时间
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"卖了第"+ticket--+"张票");
                }
            }
        }
    }
}


public class TicketDemo2 {
    public static void main(String[] args) {
        //启动三个窗口
        Ticket2 ticket1 = new Ticket2();
        Ticket2 ticket2 = new Ticket2();
        Ticket2 ticket3 = new Ticket2();
        Thread t1 = new Thread(ticket1, "窗口1");
        Thread t2 = new Thread(ticket2, "窗口2");
        Thread t3 = new Thread(ticket3, "窗口3");
        t1.start();
        t2.start();
        t3.start();


    }
}
