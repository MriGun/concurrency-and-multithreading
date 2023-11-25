package com.mri.concurrency.completablefuture;

import com.mri.concurrency.util.CommonUtil;

import java.util.List;
import java.util.concurrent.*;

public class SupplyAsyncDemo {

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Current Thread" +Thread.currentThread().getName());
                    return CommonUtil.fetchEmployees();
                }, executorService
        );

        executorService.shutdown();

       return listCompletableFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employees = supplyAsyncDemo.getEmployees();
        employees.stream().forEach(System.out::println);
    }
}
