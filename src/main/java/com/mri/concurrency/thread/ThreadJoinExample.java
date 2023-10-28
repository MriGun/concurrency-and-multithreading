package com.mri.concurrency.thread;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ThreadJoinExample {

    public static void main(String[] args) {
        FileDownloader dw1 = new FileDownloader("https://goo.gl/nqZJn4", "jugbd-meetup7_1.jpeg");
        FileDownloader dw2 = new FileDownloader("https://goo.gl/UoSMMt", "jugbd-meetup7_2.jpeg");

        DownloadingHeartBeat heartBeat = new DownloadingHeartBeat();

        dw1.start();
        dw2.start();
        heartBeat.start();

        try {
            dw1.join();
            dw2.join();
            heartBeat.shutdown();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Download is completed!");
    }

}

class  DownloadingHeartBeat extends Thread {
    private volatile boolean beating = true;

    public void run() {
        String[] dots = {
                ".",
                "..",
                "...",
                "...."
        };

        while (beating) {
            for (String dot : dots) {
                System.out.println(dot);
                try {
                    Thread.sleep(50);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        this.beating = false;
    }
}

class FileDownloader extends Thread {
    private String url;
    private String fileName;

    public FileDownloader(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public void run() {
        try {
            System.out.println("Started to download: " + fileName);

            URL resoueToDownload = new URL(url);
            URLConnection connection = resoueToDownload.openConnection();
            InputStream inputStream = connection.getInputStream();

            File filToSave = new File(fileName);
            Files.copy(inputStream, filToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();

        }
        catch (IOException e) {
            System.out.println("Failed to download the file: " + e.getMessage());
        }
    }
}
