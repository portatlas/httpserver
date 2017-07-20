package com.portatlas;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.portatlas.test_helpers.FileHelper;
import static org.junit.Assert.assertEquals;

public class FileReaderTest {
    private FileReader fileReader;
    private File tempFile;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        fileReader = new FileReader();
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
    }

    @Test
    public void testGetMediaTypeOfResourceWithOutExtension() {
        assertEquals("application/octet-stream", fileReader.getMediaType("fileReader"));
    }

    @Test
    public void testGetMediaTypeOfResourceWithExtension() {
        assertEquals("text/plain", fileReader.getMediaType("fileReader.txt"));
    }

    @Test
    public void testGetContentOfFileFromFilePath() throws IOException {
        String expectedContent = new String("testing\n".getBytes());
        String parsedContent = new String(fileReader.getContent(tempFile.getPath()));

        assertEquals(expectedContent, parsedContent);
    }

    @Test
    public void testGetCharLengthOfFile() {
        assertEquals(8, fileReader.getContentLength(tempFile.getPath()));
    }
}
