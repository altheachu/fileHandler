package com.example.fileHandler.component;

import com.example.fileHandler.utils.CommonConstant;
import com.example.fileHandler.utils.CommonMethods;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

@Component("csvJob")
@Scope("prototype")
public class CsvJob implements Callable<String>, FileJob {
    private Resource fileResource;
    @Override
    public void setFileResource(Resource fileResource){
        this.fileResource = fileResource;
    }
    @Override
    public String call(){
        try {
            CommonMethods.copyFile(fileResource);
            return String.format("file copy success: %s", this.fileResource.getFilename());
        } catch (Exception e) {
            // TODO write in error log
            return String.format("file process error: %s, message: %s", this.fileResource.getFilename(), e.getMessage());
        }
    }
}
