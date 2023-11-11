package com.mri.concurrency.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(2);

        Developer developer1 = new Developer("Developer 1", latch);
        Developer developer2 = new Developer("Developer 2", latch);

        Tester tester = new Tester("Tester 1", latch);

        executorService.execute(tester);
        executorService.execute(developer1);
        executorService.execute(developer2);


        executorService.shutdown();

    }
}
