package com.portatlas;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileReaderTest {

    private FileReader file;

    @Before
    public void setUp() {
        file = new FileReader();
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
        Directory dir = new Directory();
        final File folder = new File(dir.pathName);
        ArrayList files = dir.listFilesForFolder(folder);

        assertEquals("file1", files.get(0));
        assertEquals(dir.pathName + "file1", dir.pathName + files.get(0));
    }

    @Test
    public void testGetContentOfFileFromFilePath() {
        Directory dir = new Directory();
        final File folder = new File(dir.pathName);
        ArrayList files = dir.listFilesForFolder(folder);
        String filepath = dir.pathName + files.get(0);

        assertEquals("file1 contents", file.getContent(filepath));

    }
}
