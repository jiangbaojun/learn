package com.example.thread;

public class InterruptDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread 1 running...");
                try {
                    Thread.sleep(1000); // 模拟工作
                } catch (InterruptedException e) {
                    // 线程在 sleep 时被中断会抛出异常，此时应退出循环
                    System.out.println("Thread 1 interrupted.");
                    break;
                }
            }
            System.out.println("Thread 1 exiting.");
        });

        Thread t2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread 2 running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread 2 interrupted.");
                    break;
                }
            }
            System.out.println("Thread 2 exiting.");
        });

        // 设置守护线程
//        t1.setDaemon(true);
//        t2.setDaemon(true);

        t1.start();
        t2.start();

        // 主线程等待 5 秒后中断子线程
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        System.out.println("Main thread interrupting child threads...");
        // 中断子线程
        t1.interrupt();
        t2.interrupt();

        // 等待子线程结束
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        System.out.println("Main thread exiting.");
    }
}
