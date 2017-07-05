package com.portatlas.response;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileReaderContentResponseTest {

    private FileContentResponse fileContentResponse;

    @Before
    public void setUp() {
        fileContentResponse = new FileContentResponse();
    }

    @Test
    public void testResponseHasStatus200() {

        assertEquals(StatusCodes.OK, fileContentResponse.run("").getStatus());
    }
    
    @Test
    public void testHeaderContentTypeOfUnknownFile() {
        String resource = "file";

        assertEquals("application/octet-stream", fileContentResponse.run(resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPlainText() {
        String resource = "text-file.txt";

        assertEquals("text/plain", fileContentResponse.run(resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPNG() {
        String resource = "image.png";

        assertEquals("image/png", fileContentResponse.run(resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfJPEG() {
        String resource = "image.jpeg";

        assertEquals("image/jpeg", fileContentResponse.run(resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfGIF() {
        String resource = "image.gif";

        assertEquals("image/gif", fileContentResponse.run(resource).getHeader("Content-Type"));
    }

}
