package com.portatlas;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.mockito.Mockito;

public class DirectoryTest {
    private Directory dir;
    private final File folder = Mockito.mock(File.class);
    private File mockFile1 = new File("file1");
    private File mockFile2 = new File("file2");
    private File[] mockFolder = new File[2];

    @Before
    public void setUp() {
        dir = new Directory();
        mockFolder[0] = mockFile1;
        mockFolder[1] = mockFile2;
        Mockito.when(folder.listFiles()).thenReturn(mockFolder);
    }

    @Test
    public void testGetDefaultDir() {
        assertEquals(System.getProperty("user.dir") + "/public/", dir.pathName);
    }

    @Test
    public void testGetDirPassedAsArgs() {
        Directory notDefaultDir = new Directory(System.getProperty("user.dir"));

        assertEquals(System.getProperty("user.dir"), notDefaultDir.getPathName());
    }

    @Test
    public void testPrintFileInDir() {
        List<String> files = Arrays.asList("file1", "file2");
        assertEquals(files , dir.listFilesForFolder(folder));
    }

    @Test
    public void testHasFile() {
        dir.listFilesForFolder(folder);

        assertTrue(dir.hasFile("file1"));
        assertFalse(dir.hasFile("nofile"));
    }
}
