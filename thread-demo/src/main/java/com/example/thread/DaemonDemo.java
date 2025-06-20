package com.example.thread;

public class DaemonDemo {
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
            Thread t21 = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread 21 running...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread 21 interrupted.");
                        break;
                    }
                }
                System.out.println("Thread 2 exiting.");
            });
            //t21是守护线程，不是t2停止，t21就停止。守护线程要等jvm中所有用户线程停止后才会自动终止
            t21.setDaemon(true);
            t21.start();

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
//        t1.interrupt();
        t2.interrupt();

        System.out.println("Main thread exiting.");
    }
}
