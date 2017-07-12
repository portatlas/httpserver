package com.portatlas.response;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseSerializerTest {
    private ResponseSerializer serializer;

    @Before
    public void setUp() {
        serializer = new ResponseSerializer();
    }

    @Test
    public void testItSerializes200OK() throws IOException {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        byte[] responseWithStatusOKBytes = serializer.serialize(response);
        String responseString = new String(responseWithStatusOKBytes);

        assertEquals("HTTP/1.1 200 OK" + serializer.CRLF, responseString);
    }

    @Test
    public void testItSerializes404NotFound() throws IOException {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_FOUND)
                                    .build();
        byte[] responseWithStatusNotFound = serializer.serialize(response);
        String responseString = new String(responseWithStatusNotFound);

        assertEquals("HTTP/1.1 404 Not Found" + serializer.CRLF, responseString);
    }

    @Test
    public void testItSerializesHeaders() throws IOException {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "3")
                                    .build();
        byte[] responseWithHeaders = serializer.serialize(response);
        String responseString = new String(responseWithHeaders);

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(serializer.CRLF);

        assertEquals(builder.toString(), serializer.serialize(response));
    }

    @Test
    public void testItSerializesBodyWithText() throws IOException {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "3")
                                    .body("<a href=\"/file1\">file1</a>".getBytes())
                                    .build();
        byte[] responseWithHeaderAndBody = serializer.serialize(response);
        String responseString = new String(responseWithHeaderAndBody);

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(serializer.CRLF)
                                                   .append(serializer.CRLF)
                                                   .append("<a href=\"/file1\">file1</a>");

        assertEquals(builder.toString(), responseString);

    }
}
