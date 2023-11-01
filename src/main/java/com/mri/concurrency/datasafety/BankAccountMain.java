package com.mri.concurrency.datasafety;

import com.mri.concurrency.util.CommonUtil;

public class BankAccountMain {
    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount(100);
        Thread depositThread= new Thread(() -> {
            for (int i=0; i<100; i++) {
                bankAccount.deposit(100);
                CommonUtil.sleep();
            }
        });

        Thread withdrawThread= new Thread(() -> {
            for (int i=0; i<100; i++) {
                bankAccount.withdraw(100);
                CommonUtil.sleep();
            }
        });

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

