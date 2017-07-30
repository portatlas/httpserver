package com.portatlas.response;

import com.portatlas.constants.HeaderName;
import com.portatlas.constants.HttpVersion;
import com.portatlas.helpers.Converter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseTest {
    private Response response;
    private Converter convert;

    @Before
    public void setUp() throws Exception {
        response = new Response();
        convert = new Converter();
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
        response.setHeader(HeaderName.CONTENT_LENGTH, "88");

        assertEquals("88", response.getHeader(HeaderName.CONTENT_LENGTH));
    }

    @Test
    public void testSetBody() {
        byte[] body = "<html></html>".getBytes();
        response.setBody(body);

        assertEquals("<html></html>", convert.bytesToString(response.getBody()));
    }

    @Test
    public void testGetBodyWhenNull() {
        assertEquals(null, response.getBody());
    }
}
