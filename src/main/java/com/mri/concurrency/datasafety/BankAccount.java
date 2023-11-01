package com.mri.concurrency.datasafety;

public class BankAccount {
    private long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(long amount) {
        System.out.println("Withdrawing amount " + amount);
        long newBalance = balance - amount;
        System.out.println("New balance for withdrawing is: " + newBalance);
        balance = newBalance;
    }

    public synchronized void deposit(long amount) {
        System.out.println("Depositing amount " + amount);
        long newBalance = balance + amount;
        System.out.println("New balance for depositing is: " + newBalance);
        balance = newBalance;
    }

    public long getBalance() {
        return balance;
    }
}
