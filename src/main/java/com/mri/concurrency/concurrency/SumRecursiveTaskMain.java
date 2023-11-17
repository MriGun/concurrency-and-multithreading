package com.mri.concurrency.concurrency;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class SumRecursiveTaskMain {
    public static void main(String[] args) {
        int[] array = new int[100_00];

        Random random = new Random();
        for (int i = 0; i< args.length; i++) {
            array[i] = random.nextInt();
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        SumRecursiveTask recursiveTask = new SumRecursiveTask(array, 0, array.length);
        Long result = forkJoinPool.invoke(recursiveTask);

        System.out.println("result = " + result);
    }
}
