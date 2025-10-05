package com.example.fileHandler.controller;

import com.example.fileHandler.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class TestController {

    @Autowired
    private JobService jobService;

    @GetMapping("/test")
    public ResponseEntity<String> uiRunJobTest()throws Exception {
        String result = jobService.testJob();
        return ResponseEntity.ok(result);
    }

}
