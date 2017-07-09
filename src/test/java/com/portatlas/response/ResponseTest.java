package com.portatlas.response;

import com.portatlas.HttpVersion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseTest {

    private Response response;

    @Before
    public void setUp() throws Exception {
        response = new Response();
    }

    @Test
    public void testSetHttpVersion() {
        response.setHttpVersion(HttpVersion.CURRENT_VER);

        assertEquals(HttpVersion.CURRENT_VER, response.getHttpVersion());
    }

    @Test
    public void testSetStatus() {
        response.setStatus("200 OK");

        assertEquals("200 OK", response.getStatus());
    }

    @Test
    public void testHeaderCanBeSetAndHasHeadersFieldNameContentLengthIsTrue() {
        response.setHeader("Content-Length", "88");

        assertEquals("88", response.getHeader("Content-Length"));
    }

    @Test
    public void testSetBody() {
        response.setBody("<html></html>");

        assertEquals("<html></html>", response.getBody());
    }

}
