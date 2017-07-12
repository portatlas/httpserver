package com.portatlas;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    public String getContent(String filepath) {
        String content = "";
        File file = new File(filepath);

        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                content += line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return content;
    }

    public byte[] getImage(String filepath) throws IOException {
        File imageFile = new File(filepath);
        byte[] image = Files.readAllBytes(imageFile.toPath());

        return image;
    }
}
