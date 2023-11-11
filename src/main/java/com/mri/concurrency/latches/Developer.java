package com.mri.concurrency.latches;

import org.aopalliance.aop.AspectException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Developer implements Runnable{

    private CountDownLatch latch;
    private String name;

    public Developer(String name, CountDownLatch latch) {
        this.latch = latch;
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(String.format("Task assigned to developer: %s", name));
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println(String.format("Task finished by developer: %s", name));
        latch.countDown();

    }
}
