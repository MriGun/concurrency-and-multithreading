package com.mri.concurrency.pool;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class TextCopier implements Callable {

    private String target;

    public TextCopier(String url) {
        this.target = url;
    }

    @Override
    public Object call() throws Exception {
        URL url = new URL(target);
        StringBuilder builder = new StringBuilder();
        try(InputStream is = url.openConnection().getInputStream()){
            Scanner scanner = new Scanner(is);
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
        }
        return builder.toString();
    }
}
