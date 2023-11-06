package com.mri.concurrency.datasafety;

import com.mri.concurrency.util.CommonUtil;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferBlockingQueue {
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    public void addItem(Integer item) {
        try {
            queue.put(item);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    public Integer getItem() {
        try {
            return queue.take();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        BufferBlockingQueue buffer = new BufferBlockingQueue();
        Random random = new Random();
        Thread producer = new Thread(() -> {
            while (true)  {
                int anInt = random.nextInt();
                System.out.println("Produced: " + anInt);
                buffer.addItem(anInt);
                CommonUtil.sleepOneSecond();
            }
        });

        Thread consumer = new Thread(() -> {
            while (true)  {
                Integer item = buffer.getItem();
                System.out.println("Consumed: " + item);
                CommonUtil.sleepOneSecond();
            }
        });

        producer.start();
        consumer.start();
    }
}
