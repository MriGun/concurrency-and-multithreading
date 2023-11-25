package com.mri.concurrency.completablefuture;

import com.mri.concurrency.util.CommonUtil;

import java.util.concurrent.*;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    //public EmployeeReminderService() {}

    public Void sendReminderToEmployee() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Void completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetcheEmployee :" + Thread.currentThread().getName());
            return CommonUtil.fetchEmployees();
        }, executorService).thenApplyAsync(employees -> {
            System.out.println("ThenApply new joiner filter :" + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> employee.getNewJoiner().equals("TRUE"))
                    .toList();
        }, executorService).thenApplyAsync((employees) -> {
            System.out.println("ThenApply pending training filter :" + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> employee.getLearningPending().equals("TRUE"))
                    .collect(Collectors.toList());
        }, executorService).thenApplyAsync((employees) -> {
            System.out.println("ThenApply get Email filter :" + Thread.currentThread().getName());
            return employees.stream()
                    .map(Employee::getEmail)
                    .toList();
        }, executorService).thenAcceptAsync((emailList) -> {
            System.out.println("thenAccept send email :" + Thread.currentThread().getName());
            emailList.stream().forEach(email -> sendEmail(email));
        }, executorService)
                .get();

        /*if (completableFuture.isDone() || completableFuture.isCompletedExceptionally()) {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }*/

        executorService.shutdown();
        //executorService.awaitTermination(10, TimeUnit.SECONDS);

        return completableFuture;
    }

    public static void sendEmail(String email) {
        System.out.println("Sending email to" + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
       EmployeeReminderService service = new EmployeeReminderService();
       service.sendReminderToEmployee();
    }
}
