package com.mri.concurrency.datasafety;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopherMainWithExecutorService {
    public static void main(String[] args) {
        Lock[] forks = new Lock[5];

        for (int i = 0; i< forks.length; i++) {
            forks[i] = new ReentrantLock();
        }

        PhilosopherWithLock[] philosophers = new PhilosopherWithLock[5];

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i =0; i<philosophers.length; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i+1) % forks.length];

            philosophers[i] = new PhilosopherWithLock("Philosopher " + (i + 1), leftFork, rightFork);
            executorService.execute(philosophers[i]);

            //to remove deadlock situation
            /*if (i == philosophers.length - 1) {
                philosophers[i] = new PhilosopherWithLock("Philosopher " + (i + 1), rightFork, leftFork);
            }
            else {
                philosophers[i] = new PhilosopherWithLock("Philosopher " + (i + 1), leftFork, rightFork);
            }*/

            //philosophers[i].start();
        }

        executorService.shutdown();
    }
}
