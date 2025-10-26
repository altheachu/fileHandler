package com.example.fileHandler.utils;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CommonMethods {

    public static void copyFile(Resource resource) throws Exception {
        try {
            if (resource.getFilename() == null) {
                throw new IllegalArgumentException("lack file name");
            }
            Path outputDirectory = Paths.get(CommonConstant.targetFileDirectory);
            if (Files.notExists(outputDirectory)) {
                Files.createDirectory(outputDirectory);
                System.out.println("Created directory: " + outputDirectory);
            }
            String outputUri = String.format("%s/%s", CommonConstant.targetFileDirectory, resource.getFilename());
            Path outputPath = Paths.get(outputUri);
            try (InputStream inputStream = resource.getInputStream()) {
                Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
