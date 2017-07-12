package com.portatlas.response;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseBuilderTest {
    Converter convert = new Converter();

    @Test
    public void testCreatesResponseLine() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();

        assertEquals(StatusCodes.OK, response.getStatus());
    }

    @Test
    public void testResponseLineStatusIsNeverNull() {
        Response response = Response.builder().build();

        assertEquals(StatusCodes.OK, response.getStatus());
    }

    @Test
    public void testBuildHeaders() {
        Response response = Response.builder()
                                    .header(HeaderName.CONTENT_TYPE, "text/plain")
                                    .build();

        assertEquals("text/plain", response.getHeader(HeaderName.CONTENT_TYPE));
    }

    @Test
    public void testBuildMultipleHeaders() {
        Response response = Response.builder()
                                    .header(HeaderName.CONTENT_TYPE, "text/plain")
                                    .header(HeaderName.CONTENT_LENGTH, "10")
                                    .build();

        assertEquals("text/plain", response.getHeader(HeaderName.CONTENT_TYPE));
        assertEquals("10", response.getHeader(HeaderName.CONTENT_LENGTH));
    }

    @Test
    public void testBuildBodyWithText() {
        Response response = Response.builder()
                                    .body("foo content".getBytes())
                                    .build();

        assertEquals("foo content", Converter.bytesToString(response.getBody()));
    }
}
