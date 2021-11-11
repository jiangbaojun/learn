package com.tuling.part2;

class PriorityThread extends Thread{
    @Override
    public void run() {
        for(int i=0;i<500;i++) {
            System.out.println(Thread.currentThread().getName()+"============"+i);
        }
    }
}

public class TestPriority {
    public static void main(String[] args) {
        PriorityThread p1 = new PriorityThread();
        p1.setName("P1线程");

        PriorityThread p2 = new PriorityThread();
        p2.setName("P2线程");

        PriorityThread p3 = new PriorityThread();
        p3.setName("P3线程");

        p1.setPriority(Thread.MAX_PRIORITY);
        p3.setPriority(Thread.MIN_PRIORITY);


        p1.start();
        p2.start();
        p3.start();


    }
}
