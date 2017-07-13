package com.portatlas.TestHelpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.rules.TemporaryFolder;

public class FileHelper {
    public static File createTempFileWithContent(TemporaryFolder tempFolder) throws IOException {
        tempFolder.newFile("test_temp_file");
        tempFolder.newFile("test.jpeg");
        tempFolder.newFile("test.png");
        tempFolder.newFile("test.gif");
        return createTempTxtFile(tempFolder);
    }

    public static File createTempTxtFile(TemporaryFolder tempFolder) throws IOException {
        File createdFile = tempFolder.newFile("test_temp_file.txt");
        try{
            PrintWriter writer = new PrintWriter(createdFile, "UTF-8");
            writer.println("testing");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createdFile;
    }

    public static File createTempImageFile(TemporaryFolder temporaryFolder) throws IOException {
        File createdFile = temporaryFolder.newFile("test_temp_image.jpeg");
        byte[] image = new byte[4];
        try {
            FileOutputStream fos = new FileOutputStream(createdFile.getPath());
            fos.write(image);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createdFile;
    }
}
