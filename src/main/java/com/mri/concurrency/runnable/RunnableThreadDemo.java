package com.mri.concurrency.runnable;

import com.mri.concurrency.util.CommonUtil;

public class RunnableThreadDemo {
    public static void main(String[] args) {
        MyThreadRunnable myThreadRunnable = new MyThreadRunnable();
        Thread thread = new Thread(myThreadRunnable);
        thread.setName("Runnable thread");
        thread.start();

        for (int i =0; i<5; i++) {
            System.out.println("[" + i + "] inside " + Thread.currentThread().getName());
            CommonUtil.sleepOneSecond();
        }
    }
}
