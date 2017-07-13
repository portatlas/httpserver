package com.portatlas.response;

import com.portatlas.HttpVersion;
import com.portatlas.TestHelpers.FileHelper;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.rules.TemporaryFolder;

public class PartialContentResponseTest {
    public Request getPartialContentRequest;
    public PartialContentResponse partialContentResponse;
    public String dir;
    public File tempFile;
    public String resource = "/test_temp_file.txt";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        partialContentResponse = new PartialContentResponse();
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
        dir = tempFolder.getRoot().getPath();
        getPartialContentRequest = new Request(RequestMethod.GET, "/test_temp_file.txt", HttpVersion.CURRENT_VER);
        getPartialContentRequest.addHeader("Range", "bytes=0-4");
    }

    @Test
    public void testResponseHasStatus206() {
        assertEquals(StatusCodes.PARTIAL_CONTENT, partialContentResponse.run(getPartialContentRequest, dir, resource).getStatus());
    }

    @Test
    public void testGetRangeRequest() {
        assertEquals("0-4", partialContentResponse.getRangeRequested(getPartialContentRequest));
    }

    @Test
    public void testBuildPartialContent() throws Exception {
        String contentRangeRequest = "0-4";
        int charLengthOfFile = 8;
        byte[] fullContent = "testing\n".getBytes();
        String fullPath = dir + resource;

        String partialContentExpected = new String(Arrays.copyOfRange(fullContent, 0, 5));
        String partialContentParsed = new String(partialContentResponse.buildPartialContent(fullPath, contentRangeRequest, charLengthOfFile));

        assertEquals(partialContentExpected, partialContentParsed);
    }
}
