package com.example.fileHandler.batch;

import com.example.fileHandler.component.CsvJob;
import com.example.fileHandler.component.FileJob;
import com.example.fileHandler.component.JpegJob;
import com.example.fileHandler.utils.CommonConstant;
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

@Component
public class BatchJob {
    @Autowired
    private ExecutorService executerService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private Resource[] csvResources;
    @Autowired
    private Resource[] jpegResources;

    public String execute() {
        StringBuilder sb = new StringBuilder("job execute result: ");
        boolean isCsvTmpResource = csvResources.length == 1 && csvResources[0].getFilename().contains(CommonConstant.csvInitFilePrefix);
        boolean isJpegTmpResource = jpegResources.length == 1 && jpegResources[0].getFilename().contains(CommonConstant.jpegInitFilePrefix);
        if (isCsvTmpResource) {
            sb.append("No csv file found in resource directory. ");
        } else {
            List<Resource> csvResourceList = Arrays.stream(csvResources).toList();
            sb.append(processCsvJob(csvResourceList));
        }
        if (isJpegTmpResource) {
            sb.append("No jpeg file found in resource directory. ");
        } else {
            List<Resource> jpegResourceList = Arrays.stream(jpegResources).toList();
            sb.append(processJpegJob(jpegResourceList));
        }
        return sb.toString();
    }

    private String processCsvJob(List<Resource> csvResourceList) {

        List<String> jobResults = new ArrayList<>();
        List<CsvJob> csvJobs = transToJobs(context, csvResourceList, CsvJob.class);
        int successCnt = 0;
        int failCnt = 0;

        for (int i = 0; i < csvJobs.size(); i++) {
            Future<String> csvFuture = executerService.submit(csvJobs.get(i));
            try {
                jobResults.add(csvFuture.get());
                successCnt++;
            } catch (ExecutionException e) {
                jobResults.add(e.getMessage() + ": Error");
                failCnt++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                jobResults.add(e.getMessage() + ": Interrupted");
                failCnt++;
            }
        }
        String resultStr = String.format("%s. Total success: %s, total fail: %s.",
                        String.join(",", jobResults), successCnt, failCnt);
        return resultStr;
    }

    private String processJpegJob(List<Resource> jpegResourceList) {
        boolean isAllSuccess = true;
        List<JpegJob> jpegJobs = transToJobs(context, jpegResourceList, JpegJob.class);
        for (int i = 0; i < jpegJobs.size(); i++) {
            try {
                executerService.submit(jpegJobs.get(i));
            } catch (Exception e) {
                isAllSuccess = false;
            }
        }
        return isAllSuccess ? "all jpeg file compressed successfully. " : "not all jpeg file compressed successfully. ";
    }

    private <T extends FileJob> List<T> transToJobs(ApplicationContext context, List<Resource> resourceList, Class<T> jobClass){
        return resourceList.stream().map(resource -> {
            T job = context.getBean(jobClass);
            job.setFileResource(resource);
            return job;
        }).toList();
    }
}
