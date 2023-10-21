package com.mri.concurrency.runnable;

import com.mri.concurrency.util.CommonUtil;

public class WatchDemo implements Runnable{

    private boolean running = true;
    @Override
    public void run() {
        while (running) {
            CommonUtil.printCurrentTime();
            CommonUtil.sleepOneSecond();

            if (Thread.interrupted()) {
                System.out.println("Thread is interupted!");
                return;
            }
        }
    }

    private void shutDown() {
        this.running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        WatchDemo watch = new WatchDemo();
        Thread watchThread = new Thread(watch);
        watchThread.start();
        Thread.sleep(500);
        //watchThread.interrupt();
        watch.shutDown();
    }
}
