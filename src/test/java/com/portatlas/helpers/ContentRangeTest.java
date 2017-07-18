package com.portatlas.helpers;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.test_helpers.FileHelper;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContentRangeTest {
    private int charLengthOfFile = 72;
    private int[] partialContentRange = new int[2];
    private Request getPartialContentRequest;
    private String directoryPath;
    private File tempFile;
    private String resource = "/test_temp_file.txt";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
        directoryPath = tempFolder.getRoot().getPath();
        getPartialContentRequest = new Request(RequestMethod.GET, resource, HttpVersion.CURRENT_VER);
        getPartialContentRequest.addHeader(HeaderName.RANGE, "bytes=0-4");
    }

    @Test
    public void testParseRequestContentRangeWithStartAndEnd() {
        String contentRangeRequest = "0-4";
        partialContentRange[0] = 0;
        partialContentRange[1] = 5;

        assertTrue(Arrays.equals(partialContentRange, ContentRange.getRange(contentRangeRequest, 2)));
    }

    @Test
    public void testParseRequestContentRangeWithJustEnd() {
        String contentRangeRequest = "-6";
        partialContentRange[0] = charLengthOfFile - 6;
        partialContentRange[1] = charLengthOfFile;

        assertTrue(Arrays.equals(partialContentRange, ContentRange.getRange(contentRangeRequest, charLengthOfFile)));
    }

    @Test
    public void testParseRequestContentRangeWithStart() throws Exception {
        String contentRangeRequest = "4-";
        partialContentRange[0] = 4;
        partialContentRange[1] = charLengthOfFile;

        assertTrue(Arrays.equals(partialContentRange, ContentRange.getRange(contentRangeRequest, charLengthOfFile)));
    }

    @Test
    public void testGetRangeRequest() {
        assertEquals("0-4", ContentRange.getRangeRequested(getPartialContentRequest));
    }

    @Test
    public void testBuildPartialContent() throws Exception {
        String contentRangeRequest = "0-4";
        int charLengthOfFile = 8;
        byte[] fullContent = "testing\n".getBytes();
        String fullPath = directoryPath + resource;

        String partialContentExpected = new String(Arrays.copyOfRange(fullContent, 0, 5));
        String partialContentParsed = new String(ContentRange.buildPartialContent(fullPath, contentRangeRequest, charLengthOfFile));

        assertEquals(partialContentExpected, partialContentParsed);
    }
}
