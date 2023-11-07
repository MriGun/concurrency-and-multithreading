package com.mri.concurrency.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TextCopierApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        TextCopier mriTextCopier = new TextCopier("https://www.jugbd.org/");
        TextCopier masterDevSkill = new TextCopier("https://masterdevskills.com/");

        List<TextCopier> tasks = Arrays.asList(mriTextCopier, masterDevSkill);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (TextCopier task : tasks) {
            Future submit = executorService.submit(task);
            System.out.println(submit.get());
        }

        /*try {
            List<Future<String>> futures = executorService.invokeAll(tasks);

            for (Future<String> future: futures) {
                String result = future.get();
                System.out.println(result);
            }
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }*/

        executorService.shutdown();
    }
}
