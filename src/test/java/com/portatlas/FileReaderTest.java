package com.portatlas;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileReaderTest {

    @Test
    public void testGetMediaTypeOfResourceWithOutExtension() {
        FileReader file = new FileReader();
        String resource = "file";

        assertEquals("application/octet-stream", file.getMediaType(resource));
    }

    @Test
    public void testGetMediaTypeOfResourceWithExtension() {
        FileReader file = new FileReader();
        String resource = "file.txt";

        assertEquals("text/plain", file.getMediaType(resource));
    }

}
