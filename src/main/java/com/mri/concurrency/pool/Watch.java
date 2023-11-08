package com.mri.concurrency.pool;

import com.mri.concurrency.util.CommonUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Watch {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(() -> CommonUtil.printCurrentTime(), 0, 1, TimeUnit.SECONDS);
    }



}
