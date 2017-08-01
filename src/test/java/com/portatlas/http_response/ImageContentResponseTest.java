package com.portatlas.http_response;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.response.StatusCodes;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.portatlas.test_helpers.FileHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageContentResponseTest {
    private ImageContentResponse jpegContentResponse;
    private String tempFolderPath;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        FileHelper.createTempFileWithContent(tempFolder);
        tempFolderPath = tempFolder.getRoot().getPath() + "/";
        jpegContentResponse = new ImageContentResponse(tempFolderPath, "test.jpeg");
    }

    @Test
    public void testResponseHasStatus200() throws IOException {
        assertEquals(StatusCodes.OK, jpegContentResponse.run().getStatus());
    }

    @Test
    public void testResponseHasContentType() throws IOException{
        assertEquals("image/jpeg", jpegContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testHeaderContentTypeOfPNG() throws IOException{
        ImageContentResponse pngContentResponse = new ImageContentResponse(tempFolderPath, "test.png");

        assertEquals("image/png", pngContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testHeaderContentTypeOfGIF() throws IOException{
        ImageContentResponse gifContentResponse = new ImageContentResponse(tempFolderPath, "test.gif");

        assertEquals("image/gif", gifContentResponse.run().getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testSetBodyWithImage() throws IOException {
        byte[] image = new byte[4];
        FileHelper.createTempImageFile(tempFolder);
        ImageContentResponse jpegTempContentResponse = new ImageContentResponse(tempFolderPath, "test_temp_image.jpeg");

        assertTrue(Arrays.equals(image, jpegTempContentResponse.run().getBody()));
    }
}
