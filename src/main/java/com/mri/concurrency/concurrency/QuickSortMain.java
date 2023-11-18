package com.mri.concurrency.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class QuickSortMain {

    public static void main(String[] args) {
        List<Integer> randomNumbers = initializeSomeRandomNumbers();

        System.out.println("1st" + randomNumbers);

        QuickSortAction<Integer> quickSort = new QuickSortAction<>(randomNumbers);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(quickSort);

        System.out.println("2nd" +randomNumbers);
    }

    private static List<Integer>initializeSomeRandomNumbers() {
        final int SIZE = 10000;
        List<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            int value = (int) (Math.random() * 1000);
            randomNumbers.add(value);
        }
        return randomNumbers;
    }
}
