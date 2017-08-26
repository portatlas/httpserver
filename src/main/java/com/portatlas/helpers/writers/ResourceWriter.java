package com.portatlas.helpers.writers;

import com.portatlas.Directory;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

public class ResourceWriter {
    private static FileWriter fileWriter = null;
    private static BufferedWriter bufferedWriter = null;

    public static void createNewFile(String filePath) throws IOException {
        File createdFile = new File(filePath);
        createdFile.createNewFile();
    }

    public static void write(String filePath, String content, Directory directory) {
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
                directory.listFilesForFolder(new File(directory.getPathName()));
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedWriter, fileWriter);
        }
    }

    private static void closeQuietly(BufferedWriter bufferedWriter, FileWriter fileWriter) {
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

    public static void delete(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            throw new Exception("File Does Not Exist");
        }
    }
}
