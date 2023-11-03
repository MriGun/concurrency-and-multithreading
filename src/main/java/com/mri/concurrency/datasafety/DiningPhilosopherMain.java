package com.mri.concurrency.datasafety;

public class DiningPhilosopherMain {
    public static void main(String[] args) {
        Fork[] forks = new Fork[5];

        for (int i = 0; i< forks.length; i++) {
            forks[i] = new Fork();
        }

        Philosopher[] philosophers = new Philosopher[5];

        for (int i =0; i<philosophers.length; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i+1) % forks.length];

            philosophers[i] = new Philosopher("Philosopher " + (i + 1), leftFork, rightFork);

            philosophers[i].start();
        }
    }
}
