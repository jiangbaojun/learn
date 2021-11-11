package com.tuling.part3;

//class Person{}

class Ticket3 implements Runnable{
    private static  int ticket = 100;
    @Override
    public void run() {
        while(true){//只要有票就一直卖

            //调用同步方法
            sellTicket();
        }
    }
    /*
    synchronized 返回值类型 方法名称(形参列表){ //对当前对象（this）加锁
    // 代码（原子操作）
    }
     */
//    public synchronized void sellTicket(){//synchronized修饰普通方法，有锁：this
//        //synchronized (this){}
//        if(ticket>0){
//            //模拟一下出票时间
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+"卖了第"+ticket--+"张票");
//        }
//    }


    public synchronized static void sellTicket(){//synchronized修饰静态方法，有锁：Ticket3.class
        //synchronized (Ticket3.class){}
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


public class TicketDemo3 {
    public static void main(String[] args) {
        //启动三个窗口
        Ticket3 ticket1 = new Ticket3();
        Ticket3 ticket2 = new Ticket3();
        Thread t1 = new Thread(ticket1, "窗口1");
        Thread t2 = new Thread(ticket2, "窗口2");
        Thread t3 = new Thread(ticket2, "窗口3");
        t1.start();
        t2.start();
        t3.start();


    }
}
