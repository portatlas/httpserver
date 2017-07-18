package com.portatlas.http_response;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.response.StatusCodes;
import com.portatlas.test_helpers.FileHelper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class FileContentResponseTest {
    private String tempFolderPath;
    private FileContentResponse textFileContentResponse;
    private FileContentResponse unknownFileContentResponse;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        FileHelper.createTempFileWithContent(tempFolder);
        tempFolderPath = tempFolder.getRoot().getPath() + "/";
        textFileContentResponse = new FileContentResponse(tempFolderPath, "test_temp_file.txt");
        unknownFileContentResponse = new FileContentResponse(tempFolderPath, "test_temp_file");
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, textFileContentResponse.run().getStatus());
    }

    @Test
    public void testHeaderContentTypeOfUnknownFile() {
        assertEquals("application/octet-stream", unknownFileContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testHeaderContentTypeOfPlainText() {
        assertEquals("text/plain", textFileContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testSetBodyBasedOnFilePath() {
        assertEquals("testing\n", Converter.bytesToString(textFileContentResponse.run().getBody()));
    }
}
