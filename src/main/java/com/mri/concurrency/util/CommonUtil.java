package com.mri.concurrency.util;

import java.util.concurrent.TimeUnit;

public class CommonUtil {

    public static void sleepOneSecond() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
