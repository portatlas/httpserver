package com.portatlas.response;

import com.portatlas.Server;

import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

public class FileContentResponseTest {
    private ServerSocket serverSocket;
    private Server server = new Server(serverSocket);
    private FileContentResponse fileContentResponse;
    private String dir = System.getProperty("user.dir") + "/public/";
    private String resource = "file1";

    @Before
    public void setUp() {
        fileContentResponse = new FileContentResponse();
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
    public void testHeaderContentTypeOfPNG() {
        String resource = "image.png";

        assertEquals("image/png", fileContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfJPEG() {
        String resource = "image.jpeg";

        assertEquals("image/jpeg", fileContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfGIF() {
        String resource = "image.gif";

        assertEquals("image/gif", fileContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testSetBodyBasedOnFilePath() {

        assertEquals("file1 contents", fileContentResponse.run(dir, resource).getBody());
    }

}
