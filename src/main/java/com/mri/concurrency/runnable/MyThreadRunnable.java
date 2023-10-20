package com.mri.concurrency.runnable;

import com.mri.concurrency.util.CommonUtil;

public class MyThreadRunnable implements Runnable{
    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Thread Name : " + currentThreadName);

        for (int i =0; i<5; i++) {
            System.out.println("[" + i + "] inside " + Thread.currentThread().getName());
            CommonUtil.sleepOneSecond();
        }
    }
}
