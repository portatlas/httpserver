package com.portatlas.http_response;

import com.portatlas.Directory;
import com.portatlas.constants.HeaderName;
import com.portatlas.constants.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.helpers.ResourceReader;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;
import com.portatlas.test_helpers.FileHelper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class PatchResponseTest {
    private Request patchRequest;
    private HttpResponse patchResponse;
    private Directory directory;
    private String directoryPath;
    private String eTag = "9801739daae44ec5293d4e1f53d3f4d2d426d91c";
    private String requestTarget = "/test_temp_file.txt";

    @Before
    public void setUp() throws IOException {
        directoryPath = tempFolder.getRoot().getPath();
        directory = new Directory(directoryPath);
        patchRequest = new Request(RequestMethod.PATCH, requestTarget, HttpVersion.CURRENT_VER);
        patchResponse = new PatchResponse(patchRequest, directory);
        FileHelper.createTempFileWithContent(tempFolder);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus204() {
        patchRequest.addHeader(HeaderName.IF_MATCH, eTag);
        patchRequest.setBody("new");

        assertEquals(StatusCodes.NO_CONTENT, patchResponse.run().getStatus());
    }

    @Test
    public void testSetContentIfMatchETag() {
        patchRequest.addHeader(HeaderName.IF_MATCH, eTag);
        patchRequest.setBody("new");
        patchResponse.run();

        assertEquals("new", Converter.bytesToString(ResourceReader.getContent(directoryPath + requestTarget)));
    }

    @Test
    public void testResponseHasStatus214IfETagDoesNotMatch () {
        patchRequest.addHeader(HeaderName.IF_MATCH, "nomatch");
        patchRequest.setBody("new");

        assertEquals(StatusCodes.PRECONDITIONED_FAILED, patchResponse.run().getStatus());
    }

    @Test
    public void testDoesNotSetContentIfMatchETag() {
        patchRequest.addHeader(HeaderName.IF_MATCH, "nomatch");
        patchRequest.setBody("new");
        patchResponse.run();

        assertEquals("testing\n", Converter.bytesToString(ResourceReader.getContent(directoryPath + requestTarget)));
    }
}
