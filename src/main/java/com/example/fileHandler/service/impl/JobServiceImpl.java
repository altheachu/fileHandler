package com.example.fileHandler.service.impl;

import com.example.fileHandler.component.CsvJob;
import com.example.fileHandler.component.JpegJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class JobServiceImpl {
    ExecutorService executerService = Executors.newFixedThreadPool(2);
    @Autowired
    private CsvJob csvJob;
    @Autowired
    private JpegJob jpegJob;

    public String testJob() throws ExecutionException, InterruptedException {
        Future<String> future = executerService.submit(csvJob);
        executerService.submit(jpegJob);
        return future.get();
    }
}
