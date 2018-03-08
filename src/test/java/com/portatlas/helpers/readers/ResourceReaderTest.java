package com.portatlas.helpers.readers;

import com.portatlas.test_helpers.FileHelper;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class ResourceReaderTest {
    private File tempFile;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
    }

    @Test
    public void testGetFileExtension() {
        assertEquals("txt", ResourceReader.getFileExtension("file.txt"));
    }

    @Test
    public void testGetEmptyStringWhenFileExtensionDoesNotExist() {
        assertEquals("", ResourceReader.getFileExtension("file"));
    }

    @Test
    public void testGetMediaTypeOfResourceWithOutExtension() {
        assertEquals("text/plain", ResourceReader.getMediaType("fileReader"));
    }

    @Test
    public void testGetMediaTypeOfResourceWithExtension() {
        assertEquals("text/plain", ResourceReader.getMediaType("fileReader.txt"));
    }

    @Test
    public void testGetContentOfFileFromFilePath() throws Exception {
        String expectedContent = new String("testing\n".getBytes());
        String parsedContent = new String(ResourceReader.getContent(tempFile.getPath()));

        assertEquals(expectedContent, parsedContent);
    }

    @Test
    public void testGetContentOfFileWhenFileDoesNotExist() throws Exception {
        String expectedContent = new String("".getBytes());
        String parsedContent = new String(ResourceReader.getContent(tempFolder.getRoot().getPath() + "/doesnotexist"));

        assertEquals(expectedContent, parsedContent);
    }

    @Test
    public void testGetCharLengthOfFile() {
        assertEquals(8, ResourceReader.getContentLength(tempFile.getPath()));
    }
}
