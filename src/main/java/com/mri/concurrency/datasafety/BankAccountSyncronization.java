package com.mri.concurrency.datasafety;

import com.mri.concurrency.util.CommonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class BankAccountSyncronization {
    private long balance;

    public BankAccountSyncronization(long balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(long amount) {
        System.out.println("Inside the withdraw method. Acquiring lock!");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
        System.out.println("Withdrawing amount " + amount);
        long newBalance = balance - amount;
        System.out.println("New balance for withdrawing is: " + newBalance);
        balance = newBalance;
        System.out.println("End of withdraw. releasing lock!");
    }

    public synchronized void deposit(long amount) {
        System.out.println("Inside the deposit method. Acquiring lock!");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
        System.out.println("Depositing amount " + amount);
        long newBalance = balance + amount;
        System.out.println("New balance for depositing is: " + newBalance);
        balance = newBalance;
        System.out.println("End of deposit. releasing lock!");
    }

    public long getBalance() {
        return balance;
    }

    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount(100);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_TIME;
        Thread depositThread= new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started at: " + isoFormatter.format(LocalDateTime.now()));
            bankAccount.deposit(100);
        });

        depositThread.setName("Deposit Thread");

        Thread withdrawThread= new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started at: " + isoFormatter.format(LocalDateTime.now()));
            bankAccount.withdraw(100);
        });

        withdrawThread.setName("Withdraw Thread");

        depositThread.start();
        withdrawThread.start();

        try {
            depositThread.join();
            withdrawThread.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println("Current available balance is: " + bankAccount.getBalance());
    }
}
