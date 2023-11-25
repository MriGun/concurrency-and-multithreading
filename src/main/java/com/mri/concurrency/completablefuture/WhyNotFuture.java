package com.mri.concurrency.completablefuture;

import com.mri.concurrency.util.CommonUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class WhyNotFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<List<Integer>> future = executorService.submit(() -> {
            System.out.println("Thread = " + Thread.currentThread().getName());
            CommonUtil.sleepOneSecond();
            return Arrays.asList(1, 2, 3, 4, 5);
        });

        List<Integer> integerList = future.get();

        System.out.println(integerList);

        executorService.shutdown();


        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.get();
        completableFuture.complete("return something!");

    }
}
