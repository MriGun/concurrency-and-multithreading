package com.mri.concurrency.datasafety;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DeadLock {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void running() {
        synchronized (lock1) {
           log("Acquiring lock1");
           synchronized (lock2) {
               log("Acquiring lock2");
               System.out.println("Running");
               log("About to release lock2");
           }
            log("About to release lock1");
        }
    }

    public void walking() {
        synchronized (lock2) {
            log("Acquiring lock2");
            synchronized (lock1) {
                log("Acquiring lock1");
                System.out.println("waling");
                log("About to release lock1");
            }
            log("About to release lock2");
        }
    }

    private void log(String msg) {
        LocalDateTime now = LocalDateTime.now();
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(now);
        String threadName = Thread.currentThread().getName();
        System.out.printf("%12s %s : %s%n", time, threadName, msg);
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();

        Thread runningThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                deadLock.running();
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch (InterruptedException e) {
            Thread.currentThread().isInterrupted();
            throw new AssertionError(e);
        }

        runningThread.setName("Running Thread!");

        Thread walkingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                deadLock.walking();
            }
        });

        walkingThread.setName("Walking Thread!");

        runningThread.start();
        walkingThread.start();
    }
}
