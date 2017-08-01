package com.portatlas;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.rules.TemporaryFolder;
import com.portatlas.test_helpers.FileHelper;

public class DirectoryTest {
    private Directory dir;
    public String directoryPath;
    public File tempFile;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
        directoryPath = tempFolder.getRoot().getPath();
        dir = new Directory(directoryPath);
    }

    @Test
    public void testGetDefaultDir() {
        Directory defaultDirectory = new Directory();
        assertEquals(System.getProperty("user.dir") + "/public/", defaultDirectory.pathName);
    }

    @Test
    public void testGetDirectoryPathFromArg() {
        assertEquals(directoryPath, dir.pathName);
    }

    @Test
    public void testPrintFileInDir() {
        List<String> files = Arrays.asList("test.gif",
                                                "test.jpeg",
                                                "test.png",
                                                "test_temp_file",
                                                "test_temp_file.txt");
        assertEquals(files , dir.files);
    }

    @Test
    public void testHasFile() {
        assertTrue(dir.hasFile("test.gif"));
        assertFalse(dir.hasFile("nofile"));
    }
}
