package com.example.fileHandler.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component("csvJob")
public class CsvJob implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "jobdone";
    }
}
