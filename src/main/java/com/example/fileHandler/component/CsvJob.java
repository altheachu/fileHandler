package com.example.fileHandler.component;

import com.example.fileHandler.constant.CommonConstant;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

@Component("csvJob")
@Scope("prototype")
public class CsvJob implements Callable<String> {
    private Resource fileResource;
    public void setFileResource(Resource fileResource){
        this.fileResource = fileResource;
    }
    @Override
    public String call(){
        try {
            Path outputPath = Paths.get(CommonConstant.targetFileDirectory);
            if(Files.notExists(outputPath)){
                Files.createDirectory(outputPath);
                System.out.println("Created directory: " + outputPath);
            }
            if (fileResource.getFilename()==null) throw new IllegalArgumentException("lack file name");
            try(InputStream inputStream = fileResource.getInputStream()){
                Files.copy(inputStream, outputPath, StandardCopyOption.ATOMIC_MOVE);
            }
            return String.format("file copy success: %s", this.fileResource.getFilename());
        } catch (Exception e){
            // TODO write in error log
            return String.format("file process error: %s, message: %s", this.fileResource.getFilename(), e.getMessage());
        }
    }
}
