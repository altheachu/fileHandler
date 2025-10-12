package com.example.fileHandler.controller;

import com.example.fileHandler.batch.BatchJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class TestController {

    @Autowired
    private BatchJob batchJob;

    @GetMapping("/test")
    public ResponseEntity<String> uiRunBatchJobTest()throws Exception {
        String result = batchJob.execute();
        return ResponseEntity.ok(result);
    }

}
