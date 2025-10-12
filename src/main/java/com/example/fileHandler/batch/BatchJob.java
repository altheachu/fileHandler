package com.example.fileHandler.batch;

import com.example.fileHandler.component.CsvJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class BatchJob {
    @Autowired
    private ExecutorService executerService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private Resource[] csvResources;

    public String execute(){

        List<Resource> csvResourceList = Arrays.stream(csvResources).toList();
        String csvJobResult = processCsvJob(csvResourceList);
//        executerService.submit(jpegJob);
        return csvJobResult;
    }

    private String processCsvJob(List<Resource> csvResourceList){

        List<String> jobResults = new ArrayList<>();

        List<Callable<String>> csvJobs = csvResourceList.stream().map(csvResource->{
            CsvJob csvJob = context.getBean(CsvJob.class);
            csvJob.setFileResource(csvResource);
            return (Callable<String>) csvJob;
        }).collect(Collectors.toList());

        for (int i = 0; i < csvJobs.size(); i++){
            Future<String> csvFuture = executerService.submit(csvJobs.get(i));
            try {
                jobResults.add(csvFuture.get());
            } catch (ExecutionException e) {
                jobResults.add(e.getMessage() + ": Error");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                jobResults.add(e.getMessage() + ": Interrupted");
            }
        }
        return String.join(",", jobResults);
    }

}
