package com.portatlas.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageContentResponseTest {

    private ImageContentResponse imgContentResponse;
    private String dir = System.getProperty("user.dir") + "/public/";
    private String resourceJpeg = "image.jpeg";

    @Before
    public void setUp() {
        imgContentResponse = new ImageContentResponse();
    }

    @Test
    public void testResponseHasStatus200() throws IOException {

        assertEquals(StatusCodes.OK, imgContentResponse.run(dir, resourceJpeg).getStatus());
    }

    @Test
    public void testResponseHasContentType() throws IOException{

        assertEquals("image/jpeg", imgContentResponse.run(dir, resourceJpeg).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPNG() throws IOException{
        String resource = "image.png";

        assertEquals("image/png", imgContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfJPEG() throws IOException{
        String resource = "image.jpeg";

        assertEquals("image/jpeg", imgContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfGIF() throws IOException{
        String resource = "image.gif";

        assertEquals("image/gif", imgContentResponse.run(dir, resource).getHeader("Content-Type"));
    }

    @Test
    public void testSetBodyWithImage() throws IOException {
        String filepath = dir + "/image.jpeg";
        File imageFile = new File(filepath);

        byte[] image = Files.readAllBytes(imageFile.toPath());

        assertTrue(Arrays.equals(image, imgContentResponse.run(dir, resourceJpeg).getBody()));
    }

}
