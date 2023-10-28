package com.mri.concurrency.thread;

import java.util.concurrent.TimeUnit;

public class WaitingForThreadToBeFinished {
    private static boolean doneWorking = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            findTheTop20FibonacciNumber();
            doneWorking = true;
        });

        t1.start();

        try {
            //Let's put the main thread in sleep for a moment
            TimeUnit.MILLISECONDS.sleep(500);
            if (doneWorking) {
                System.out.println("Thread t1 has finished the working!");
            }
            else {
                System.out.println("Thread t1 did not finish the working!");
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void findTheTop20FibonacciNumber() {
        for (int i=1; i<=20; i++) {
            System.out.println(fibonacci(i) + ", ");
        }
    }

    private static int fibonacci(int n) {
        if (n == 1 || n==2) {
            return 1;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }


}
