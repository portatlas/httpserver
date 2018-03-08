package com.portatlas.helpers.http_helpers;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.test_helpers.FileHelper;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class ContentRangeTest {
    private int charLengthOfFile = 72;
    private int[] partialContentRange = new int[2];
    private Request getPartialContentRequest;
    private String directoryPath;
    private String resource = "/test_temp_file.txt";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        FileHelper.createTempFileWithContent(tempFolder);
        directoryPath = tempFolder.getRoot().getPath();
        getPartialContentRequest = new Request(RequestMethod.GET, resource, HttpVersion.CURRENT_VER);
        getPartialContentRequest.addHeader(HeaderName.RANGE, "bytes=0-4");
    }

    @Test
    public void testParseRequestContentRangeWithStartAndEnd() {
        String contentRangeRequest = "0-4";
        partialContentRange[0] = 0;
        partialContentRange[1] = 5;

        assertArrayEquals(partialContentRange, ContentRange.getRange(contentRangeRequest, charLengthOfFile));
    }

    @Test
    public void testParseRequestContentRangeWithJustEnd() {
        String contentRangeRequest = "-6";
        partialContentRange[0] = charLengthOfFile - 6;
        partialContentRange[1] = charLengthOfFile;

        assertArrayEquals(partialContentRange, ContentRange.getRange(contentRangeRequest, charLengthOfFile));
    }

    @Test
    public void testParseRequestContentRangeWithStart() {
        String contentRangeRequest = "4-";
        partialContentRange[0] = 4;
        partialContentRange[1] = charLengthOfFile;

        assertArrayEquals(partialContentRange, ContentRange.getRange(contentRangeRequest, charLengthOfFile));
    }

    @Test
    public void testGetRangeRequest() {
        assertEquals("0-4", ContentRange.getRangeRequested(getPartialContentRequest));
    }

    @Test
    public void testBuildPartialContent() {
        String contentRangeRequest = "0-4";
        int charLengthOfFile = 8;
        byte[] fullContent = "testing\n".getBytes();
        String fullPath = directoryPath + resource;

        String partialContentExpected = new String(Arrays.copyOfRange(fullContent, 0, 5));
        String partialContentParsed = new String(ContentRange.buildPartialContent(fullPath, contentRangeRequest, charLengthOfFile));

        assertEquals(partialContentExpected, partialContentParsed);
    }
}
