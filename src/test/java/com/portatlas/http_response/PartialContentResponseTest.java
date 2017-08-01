package com.portatlas.http_response;

import com.portatlas.constants.HttpVersion;
import com.portatlas.response.StatusCodes;
import com.portatlas.test_helpers.FileHelper;
import com.portatlas.helpers.Converter;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class PartialContentResponseTest {
    public Request getPartialContentRequest;
    public Request fullContentRequest;
    public PartialContentResponse partialContentResponse;
    public String directoryPath;
    public String resource = "/test_temp_file.txt";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        FileHelper.createTempFileWithContent(tempFolder);
        directoryPath = tempFolder.getRoot().getPath();
        fullContentRequest = new Request(RequestMethod.GET, "/test_temp_file.txt", HttpVersion.CURRENT_VER);
        getPartialContentRequest = new Request(RequestMethod.GET, "/test_temp_file.txt", HttpVersion.CURRENT_VER);
        getPartialContentRequest.addHeader("Range", "bytes=0-4");
    }

    @Test
    public void testResponseHasStatus206ForRangeRequest() {
        partialContentResponse = new PartialContentResponse(fullContentRequest, directoryPath, resource);

        assertEquals(StatusCodes.OK, partialContentResponse.run().getStatus());
    }

    @Test
    public void testResponseHasStatus200ForRequestWithOutRange() {
        partialContentResponse = new PartialContentResponse(getPartialContentRequest, directoryPath, resource);

        assertEquals(StatusCodes.PARTIAL_CONTENT, partialContentResponse.run().getStatus());
    }

    @Test
    public void testResponseHasBodyWithPartialContent() throws Exception {
        partialContentResponse = new PartialContentResponse(getPartialContentRequest, directoryPath, resource);

        assertEquals("testi", Converter.bytesToString(partialContentResponse.run().getBody()));
    }
}
