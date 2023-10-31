package com.mri.concurrency.runnable;

import com.mri.concurrency.exception.CustomUncaughtExceptionHandler;

import java.util.concurrent.TimeUnit;

public class ThreadUncheckedExceptionExample {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError(e);
                //System.err.println("Thread interrupted due to " + e.getMessage());
            }
            throw new RuntimeException("Goodbye cruel world!");
        });

        t.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
        t.start();
    }
}
