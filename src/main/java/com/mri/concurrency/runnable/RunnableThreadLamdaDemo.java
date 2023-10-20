package com.mri.concurrency.runnable;

public class RunnableThreadLamdaDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        });

        thread.start();

        //using lamda epression
        Thread thread2 = new Thread(() -> {
            doWork();
        });

        thread2.start();
    }

    private static void doWork() {
        System.out.println("Doing some work :)");
    }
}
