package com.mri.concurrency.completablefuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {
     public Void saveEmployees(File jsonFile) throws ExecutionException, InterruptedException {

         ObjectMapper objectMapper = new ObjectMapper();
         CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(new Runnable() {
             @Override
             public void run() {

                 try {
                     List<Employee> employeeList = objectMapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                     });
                     //write logic to save list to database
                     System.out.println("Thread = " + Thread.currentThread().getName());
                     //employeeList.stream().forEach(System.out::println);
                     System.out.println(employeeList.size());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         });
         return runAsyncFuture.get();
     }


    public Void saveEmployeesWithLambda(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(
                () -> {

                    try {
                        List<Employee> employeeList = objectMapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                        });
                        //write logic to save list to database
                        System.out.println("Thread = " + Thread.currentThread().getName());
                        //employeeList.stream().forEach(System.out::println);
                        System.out.println(employeeList.size());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }, executor);
        return runAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployees(new File("employees.json"));
        runAsyncDemo.saveEmployeesWithLambda(new File("employees.json"));
    }
}
