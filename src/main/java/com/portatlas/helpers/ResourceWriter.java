package com.portatlas.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResourceWriter {
    private static FileWriter fileWriter = null;
    private static BufferedWriter bufferedWriter = null;

    public static void createNewFile(String filePath) throws IOException {
        File createdFile = new File(filePath);
        createdFile.createNewFile();
    }

    public static void write(String filePath, String content) {
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if(fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(String filePath) {
        try{
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
