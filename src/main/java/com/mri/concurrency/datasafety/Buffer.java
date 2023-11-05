package com.mri.concurrency.datasafety;

import com.mri.concurrency.util.CommonUtil;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final static int SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();
    private final Object lock = new Object();

    public  void addItem(int item) {
        synchronized(lock) {
            while (queue.size() == SIZE) {
                CommonUtil.log("Size is full, let's wait!");
                try {
                    lock.wait();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }
            CommonUtil.log("Thread resumed!");
            CommonUtil.log("Adding item: " + item);
            queue.add(item);
            CommonUtil.log("Item added. Let's notify lal");
            lock.notifyAll();
        }

    }

    public  Integer getItem() {
        synchronized(lock) {
            while (queue.isEmpty()) {
                CommonUtil.log("Queue is empty, let's wait!");
                try {
                    lock.wait();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }
        }

        CommonUtil.log("resumed!");
        CommonUtil.log("Let's consume value: ");

        Integer value = queue.poll();
        CommonUtil.log("Consume value: " + value);
        lock.notifyAll();

        return value;
    }

    public static void main(String[] args) {

        Buffer buffer = new Buffer();
        Thread pruducerThread= new Thread(() -> {
            buffer.addItem(5);
        });

        Thread consumerThread= new Thread(() -> {
            buffer.getItem();
        });

        pruducerThread.start();
        consumerThread.start();
    }

}
