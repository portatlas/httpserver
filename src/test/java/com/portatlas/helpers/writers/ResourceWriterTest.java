package com.portatlas.helpers.writers;

import com.portatlas.Directory;

import java.io.File;
import java.io.IOException;

import com.portatlas.helpers.readers.ResourceReader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ResourceWriterTest {
    private Directory directory;
    private String filePath;
    private ResourceReader resourceReader;
    private String newResource = "/newlycreatedfile";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        directory = new Directory(tempFolder.getRoot().getPath());
        filePath = tempFolder.getRoot().getPath();
        resourceReader = new ResourceReader();
        ResourceWriter.createNewFile(filePath + newResource);
    }

    @Test
    public void testCreateNewFile() {
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertTrue(directory.hasFile("newlycreatedfile"));
    }

    @Test
    public void testWriteContentToFileCreated() throws Exception {
        String content = "writing new content daily";
        ResourceWriter.write(filePath + newResource, content, directory);
        String parsedContent = new String(resourceReader.getContent(filePath + newResource));

        assertEquals(content, parsedContent);
    }

    @Test
    public void testItThrowsErrorIfDeletingAFileThatDoesNotExist() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("File Does Not Exist");
        ResourceWriter.delete(filePath + "/nofile");
    }


    @Test
    public void testItDeletesTheFileIfItExists() throws Exception {
        tempFolder.newFile("tempfiletodelete");
        ResourceWriter.delete(filePath + "/tempfiletodelete");
        directory.listFilesForFolder(new File(tempFolder.getRoot().getPath()));

        assertFalse(directory.hasFile("tempfiletodelete"));
    }
}
