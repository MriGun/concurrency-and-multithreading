package com.mri.concurrency.thread;

public class ExtendedThreadDemo {
    public static void main(String[] args) {
        ExtendedThread extendedThread = new ExtendedThread();
        extendedThread.setName("Extended Thread");
        extendedThread.start();
    }
}
