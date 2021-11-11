package com.tuling.part2;

class Task1 implements Runnable{

    @Override
    public void run() {
        for (int i = 0;i < 100;i++){
            System.out.println("A:"+i);
        }
    }
}

class Task2 implements Runnable{

    @Override
    public void run() {
        for (int i = 0;i < 100;i++){
            System.out.println("B:"+i);
            //让步
            Thread.yield();
        }
    }
}

public class TestYield {
    public static void main(String[] args) {
//        Task1 task1 = new Task1();
//        Thread thread = new Thread(task1);
//        thread.start();
        //匿名对象，这个方法只需要使用一次
        new Thread(new Task1()).start();
        new Thread(new Task2()).start();



    }
}
