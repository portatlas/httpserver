package com.portatlas.response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseBuilderTest {

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
                                    .header("Content-Type", "text/plain")
                                    .build();

        assertEquals("text/plain", response.getHeader("Content-Type"));
    }

    @Test
    public void testBuildMultipleHeaders() {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "10")
                                    .build();

        assertEquals("text/plain", response.getHeader("Content-Type"));
        assertEquals("10", response.getHeader("Content-Length"));
    }

    @Test
    public void testBuildBody() {
        Response response = Response.builder()
                                    .body("foo content")
                                    .build();

        assertEquals("foo content", response.getBody());
    }
    
}
