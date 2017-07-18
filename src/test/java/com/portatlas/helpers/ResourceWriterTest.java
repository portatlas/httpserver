package com.portatlas.helpers;

import com.portatlas.Directory;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.rules.TemporaryFolder;

public class ResourceWriterTest {
    private Directory directory;
    private String filePath;
    private ResourceReader resourceReader;

    @Before
    public void setUp() throws IOException {
        directory = new Directory(tempFolder.getRoot().getPath());
        filePath = tempFolder.getRoot().getPath();
        resourceReader = new ResourceReader();
        ResourceWriter.createNewFile(filePath + "/newlycreatedfile");
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCreateNewFile() throws Exception {
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertTrue(directory.hasFile("newlycreatedfile"));
    }

    @Test
    public void testWriteContentToFileCreated() throws Exception {
        String content = "writing new content daily";
        ResourceWriter.write(filePath + "/newlycreatedfile", content);
        String parsedContent = new String(resourceReader.getContent(filePath + "/newlycreatedfile"));

        assertEquals(content, parsedContent);
    }

    @Test
    public void testItDeletesFile() throws Exception {
        ResourceWriter.delete(filePath + "/tempfiletodelete");
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertFalse(directory.hasFile("tempfiletodelete"));
    }
}
