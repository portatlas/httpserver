package com.portatlas;

import java.util.HashMap;

public class FileReader {
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
}
