package com.mri.concurrency.datasafety;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread{
    private String name;
    private Fork leftFork;
    private Fork rightFork;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
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
        synchronized (leftFork) {
            log("grabbed left fork");
            synchronized (rightFork) {
                log("grabbed right fork");
                eat();
                log("put down the right fork");
            }
            log("put down the left fork");
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
