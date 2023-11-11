package com.mri.concurrency.datasafety;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class PhilosopherWithLock implements Runnable{
    private String name;
    private final Lock leftFork;
    private final Lock rightFork;

    public PhilosopherWithLock(String name, Lock leftFork, Lock rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {
        log("Thinking...");
    }

    private void eat() {
        //assume, eating requires some time.
        //let's put a random number
        try {
           log("Eating...");
           int eatingTime = getRandomEatingTime();
            TimeUnit.NANOSECONDS.sleep(eatingTime);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            keepThinkingAndEating();
        }
    }

    public void keepThinkingAndEating() {
        think();
        if (leftFork.tryLock()) {
            try {
                log("grabbed left fork");
                if (rightFork.tryLock()) {
                    try {
                        log("grabbed right fork");
                        eat();
                    }
                    finally {
                        log("put down the right fork");
                        rightFork.unlock();
                    }
                }
            }
            finally {
                log("put down the left fork");
                leftFork.unlock();
            }
        }
    }

    private void log(String msg) {
        LocalDateTime now = LocalDateTime.now();
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(now);
        String threadName = Thread.currentThread().getName();
        System.out.printf("%12s %s : %s%n", time, threadName, msg);
        System.out.flush();
    }

    private static int getRandomEatingTime() {
        Random random = new Random();
        return random.nextInt(100) + 50;
    }

}
