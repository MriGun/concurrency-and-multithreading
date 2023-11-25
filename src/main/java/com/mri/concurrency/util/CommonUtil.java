package com.mri.concurrency.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mri.concurrency.completablefuture.Employee;

import javax.imageio.IIOException;
import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public static void delayMin(int min) {
        try {
            TimeUnit.MINUTES.sleep(min);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(1);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void printCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s a");
        String formattedCurrentTime = LocalDateTime.now().format(formatter);
        System.out.println(formattedCurrentTime);
    }

    public static void log(String msg) {
        LocalDateTime now = LocalDateTime.now();
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(now);
        String threadName = Thread.currentThread().getName();
        System.out.printf("%12s %s : %s%n", time, threadName, msg);
    }

    public static List<Employee> fetchEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("employees.json"), new TypeReference<List<Employee>>() {});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
