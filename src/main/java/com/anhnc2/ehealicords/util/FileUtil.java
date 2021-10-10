package com.anhnc2.ehealicords.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {
    private FileUtil() {}

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = Files.createTempFile("avatar", ".tmp").toFile();
        multipartFile.transferTo(file);
        return file;
    }

    public static String appendCurrentTimeMillisToName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, dotIndex) +
                "_" +
                System.currentTimeMillis() +
                fileName.substring(dotIndex);
    }
}
