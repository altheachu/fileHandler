package com.example.fileHandler.service;

import com.example.fileHandler.component.CsvJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public interface JobService {

    public String testJob();
}
