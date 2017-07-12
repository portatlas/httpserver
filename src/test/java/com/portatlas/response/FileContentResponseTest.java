package com.portatlas.response;

import com.portatlas.helpers.Converter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileContentResponseTest {
    private FileContentResponse fileContentResponse;
    private String dir = System.getProperty("user.dir") + "/public/";
    private String resource = "file1";
    private Converter convert;

    @Before
    public void setUp() {
        fileContentResponse = new FileContentResponse();
        convert = new Converter();
    }

    @Test
    public void testResponseHasStatus200() {

        assertEquals(StatusCodes.OK, fileContentResponse.run(dir, resource).getStatus());
    }

    @Test
    public void testHeaderContentTypeOfUnknownFile() {

        assertEquals("application/octet-stream", fileContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPlainText() {
        String resource = "text-file.txt";

        assertEquals("text/plain", fileContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testSetBodyBasedOnFilePath() {

        assertEquals("file1 contents", convert.bytesToString(fileContentResponse.run(dir, resource).getBody()));
    }

}
