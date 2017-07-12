package com.portatlas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileReaderTest {

    private FileReader file;
    private Directory dir;


    @Before
    public void setUp() {
        file = new FileReader();
        dir = new Directory();
    }

    @Test
    public void testGetMediaTypeOfResourceWithOutExtension() {
        String resource = "file";

        assertEquals("application/octet-stream", file.getMediaType(resource));
    }

    @Test
    public void testGetMediaTypeOfResourceWithExtension() {
        String resource = "file.txt";

        assertEquals("text/plain", file.getMediaType(resource));
    }

    @Test
    public void testGetFirstFileInDir() {
        final File folder = new File(dir.pathName);
        ArrayList files = dir.listFilesForFolder(folder);

        assertEquals("file1", files.get(0));
        assertEquals(dir.pathName + "file1", dir.pathName + files.get(0));
    }

    @Test
    public void testGetContentOfFileFromFilePath() {
        final File folder = new File(dir.pathName);
        ArrayList files = dir.listFilesForFolder(folder);

        String filepath = dir.pathName + files.get(0);

        assertEquals("file1 contents", file.getContent(filepath));
    }

    @Test
    public void testGetImageOfFileFromFilePath() throws IOException {
        final File folder = new File(dir.pathName);
        String filepath = dir.pathName + "/image.jpeg";
        File imageFile = new File(filepath);

        byte[] image = Files.readAllBytes(imageFile.toPath());

        assertTrue(Arrays.equals(image, file.getImage(filepath)));
    }
}
