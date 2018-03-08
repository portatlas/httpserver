package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;
import com.portatlas.test_helpers.FileHelper;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class GetHandlerTest {
    private String tempFolderPath;
    private Directory directory;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private GetHandler getRootResponse;
    private Request getTextFileRequest = new Request(RequestMethod.GET, "/test_temp_file.txt" , HttpVersion.CURRENT_VER);
    private GetHandler getTextFileContentResponse;
    private Request getMissingFileRequest = new Request(RequestMethod.GET, "/filedoesnotexist.txt" , HttpVersion.CURRENT_VER);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        FileHelper.createTempTxtFile(tempFolder);
        tempFolderPath = tempFolder.getRoot().getPath();
        directory = new Directory(tempFolderPath);
        getTextFileContentResponse = new GetHandler(getTextFileRequest, directory);
    }

    @Test
    public void testResponseHasStatus404IfFileDoesNotExist() {
        GetHandler noFileResponse = new GetHandler(getMissingFileRequest, directory);
        assertEquals(StatusCodes.NOT_FOUND, noFileResponse.run().getStatus());
    }

    @Test
    public void testGetRootRequestReturnsResponseWithStatus200() throws Exception {
        getRootResponse = new GetHandler(getRootRequest, directory);
        assertEquals(StatusCodes.OK, getRootResponse.run().getStatus());
    }

    @Test
    public void testGetResourceReturnsResponseWithStatus200() {
        directory.listFilesForFolder(new File(tempFolderPath));
        assertEquals(StatusCodes.OK, getTextFileContentResponse.run().getStatus());
    }

    @Test
    public void testHeaderContentTypeOfPlainText() {
        assertEquals("text/plain", getTextFileContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testSetBodyBasedOnFilePath() {
        assertEquals("testing\n", Converter.bytesToString(getTextFileContentResponse.run().getBody()));
    }

    @Test
    public void testResponseHasMatchingContentType() {
        assertEquals("text/plain", getTextFileContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testRangeRequestReturnsResponseWithStatus206WithPartialContentInBody() {
        Request getPartialContentRequest = new Request(RequestMethod.GET, "/test_temp_file.txt", HttpVersion.CURRENT_VER);
        getPartialContentRequest.addHeader(HeaderName.RANGE, "bytes=0-4");
        GetHandler getPartialContentResponse = new GetHandler(getPartialContentRequest, directory);

        assertEquals(StatusCodes.PARTIAL_CONTENT, getPartialContentResponse.run().getStatus());
        assertEquals("testi", Converter.bytesToString(getPartialContentResponse.run().getBody()));
    }
}
