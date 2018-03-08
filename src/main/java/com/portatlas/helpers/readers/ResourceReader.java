package com.portatlas.helpers.readers;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ResourceReader {
    public static String getFileExtension(String fileName) {
        String extension = "";
        if(fileName.contains(".")) {
            int startIndex = fileName.indexOf(".") + 1;
            int endIndex = fileName.length();
            extension = fileName.substring(startIndex, endIndex);
        }
        return extension;
    }

    public static String getMediaType(String resource) {
        String fileExtension = getFileExtension(resource);
        Map<String, String> fileMediaType = new HashMap<String, String> () {{
            put("", "text/plain");
            put("txt", "text/plain");
            put("png", "image/png");
            put("jpeg", "image/jpeg");
            put("gif", "image/gif");
        }};
        return fileMediaType.getOrDefault(fileExtension, "application/octet-stream");
    }

    public static byte[] getContent(String filepath) throws Exception {
        byte[] content = new byte[0];
        File targetFile = new File(filepath);
        if (targetFile.exists() && targetFile.isFile()) {
            try {
                content = Files.readAllBytes(targetFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static int getContentLength(String filepath) {
        byte[] content = new byte[0];;
        try {
             content = getContent(filepath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return content.length;
    }
}
