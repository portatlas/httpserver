package com.portatlas.response;

import com.portatlas.TestHelpers.FileHelper;
import com.portatlas.helpers.Converter;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class FileContentResponseTest {
    private FileContentResponse fileContentResponse;
    private File tempFile;
    private String tempFolderPath;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        fileContentResponse = new FileContentResponse();
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
        tempFolderPath = tempFolder.getRoot().getPath() + "/";
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, fileContentResponse.run(tempFolderPath, "test_temp_file.txt").getStatus());
    }

    @Test
    public void testHeaderContentTypeOfUnknownFile() {
        assertEquals("application/octet-stream", fileContentResponse.run(tempFolderPath, "test_temp_file").getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPlainText() {
        assertEquals("text/plain", fileContentResponse.run(tempFolderPath, "test_temp_file.txt").getHeader("Content-Type"));
    }

    @Test
    public void testSetBodyBasedOnFilePath() {
        assertEquals("testing\n", Converter.bytesToString(fileContentResponse.run(tempFolderPath, "test_temp_file.txt").getBody()));
    }
}
