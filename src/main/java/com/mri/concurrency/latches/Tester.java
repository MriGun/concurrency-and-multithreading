package com.mri.concurrency.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Tester implements Runnable{

    private CountDownLatch latch;
    private String name;

    public Tester(String name, CountDownLatch latch) {
        this.latch = latch;
        this.name = name;
    }
    @Override
    public void run() {

        //System.out.println(String.format("Task %s is waiting for developer to finish their work", name));

        try {
            System.out.println(String.format("Task %s is waiting for developer to finish their work", name));
            latch.await();
            //TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println(String.format("Testing done by tester: %s", name));
    }
}
