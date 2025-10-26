package com.example.fileHandler.component;

import com.example.fileHandler.utils.CommonMethods;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component("jpegJob")
@Scope("prototype")
public class JpegJob implements Runnable, FileJob {
    private Resource fileResource;
    @Override
    public void setFileResource(Resource fileResource){
        this.fileResource = fileResource;
    }
    @Override
    public void run() {
        try {
            CommonMethods.copyFile(fileResource);
        } catch (Exception e) {
            // TODO write in error log
        }
    }
}
