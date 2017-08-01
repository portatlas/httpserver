package com.portatlas.helpers;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class ResourceReader {
    public static String getMediaType(String resource) {
        String fileExtension = getFileExtension(resource);
        HashMap<String, String> fileMediaType = new HashMap<String, String> () {{
            put("txt", "text/plain");
            put("png", "image/png");
            put("jpeg", "image/jpeg");
            put("gif", "image/gif");
        }};
        return fileMediaType.getOrDefault(fileExtension, "application/octet-stream");
    }

    private static String getFileExtension(String fileName) {
        int startIndex = fileName.indexOf(".") + 1;
        int endIndex = fileName.length();
        return fileName.substring(startIndex, endIndex);
    }

    public static byte[] getContent(String filepath) {
        byte[] content = new byte[0];
        File targetFile = new File(filepath);
        if (targetFile.isFile()){
            try {
                content = Files.readAllBytes(targetFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static int getContentLength(String filepath) {
        byte[] content = getContent(filepath);
        return content.length;
    }
}
