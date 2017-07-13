package com.portatlas.response;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.portatlas.TestHelpers.FileHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageContentResponseTest {
    private ImageContentResponse imgContentResponse;
    private File tempFile;
    private File tempImage;
    private String resourceJpeg = "test.jpeg";
    private String tempFolderPath;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        imgContentResponse = new ImageContentResponse();
        tempFile = FileHelper.createTempFileWithContent(tempFolder);
        tempFolderPath = tempFolder.getRoot().getPath() + "/";
    }

    @Test
    public void testResponseHasStatus200() throws IOException {
        assertEquals(StatusCodes.OK, imgContentResponse.run(tempFolderPath, resourceJpeg).getStatus());
    }

    @Test
    public void testResponseHasContentType() throws IOException{
        assertEquals("image/jpeg", imgContentResponse.run(tempFolderPath, resourceJpeg).getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfPNG() throws IOException{
        assertEquals("image/png", imgContentResponse.run(tempFolderPath, "test.png").getHeader("Content-Type"));
    }

    @Test
    public void testHeaderContentTypeOfGIF() throws IOException{
        assertEquals("image/gif", imgContentResponse.run(tempFolderPath, "test.gif").getHeader("Content-Type"));
    }

    @Test
    public void testSetBodyWithImage() throws IOException {
        byte[] image = new byte[4];
        tempImage = FileHelper.createTempImageFile(tempFolder);

        assertTrue(Arrays.equals(image, imgContentResponse.run(tempFolderPath, "test_temp_image.jpeg").getBody()));
    }
}
