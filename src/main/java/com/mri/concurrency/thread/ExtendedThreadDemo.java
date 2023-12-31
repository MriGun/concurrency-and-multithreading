package com.mri.concurrency.thread;

import com.mri.concurrency.util.CommonUtil;

public class ExtendedThreadDemo {
    public static void main(String[] args) {
        ExtendedThread extendedThread = new ExtendedThread();
        extendedThread.setName("Extended Thread");
        extendedThread.start();

        for (int i =0; i<5; i++) {
            System.out.println("[" + i + "] inside " + Thread.currentThread().getName());
            CommonUtil.sleepOneSecond();
        }
    }
}
