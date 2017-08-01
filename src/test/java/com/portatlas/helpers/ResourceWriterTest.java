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
    private String newResource = "/newlycreatedfile";

    @Before
    public void setUp() throws IOException {
        directory = new Directory(tempFolder.getRoot().getPath());
        filePath = tempFolder.getRoot().getPath();
        resourceReader = new ResourceReader();
        ResourceWriter.createNewFile(filePath + newResource);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCreateNewFile() {
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertTrue(directory.hasFile("newlycreatedfile"));
    }

    @Test
    public void testWriteContentToFileCreated() {
        String content = "writing new content daily";
        ResourceWriter.write(filePath + newResource, content);
        String parsedContent = new String(resourceReader.getContent(filePath + newResource));

        assertEquals(content, parsedContent);
    }

    @Test
    public void testItDeletesFile() {
        ResourceWriter.delete(filePath + "/tempfiletodelete");
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertFalse(directory.hasFile("tempfiletodelete"));
    }
}
