package com.portatlas.response;

import com.portatlas.Directory;
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
    public void testItSerializes200OK() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();

        assertEquals("HTTP/1.1 200 OK" + serializer.CRLF, serializer.serialize(response));
    }

    @Test
    public void testItSerializes404NotFound() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_FOUND)
                                    .build();

        assertEquals("HTTP/1.1 404 Not Found" + serializer.CRLF, serializer.serialize(response));
    }

    @Test
    public void testItSerializesHeaders() {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "3")
                                    .build();

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(serializer.CRLF);

        assertEquals(builder.toString(), serializer.serialize(response));
    }

    @Test
    public void testItSerializesBody() {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "3")
                                    .body("<a href=\"/file1\">file1</a>")
                                    .build();

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(serializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(serializer.CRLF)
                                                   .append(serializer.CRLF)
                                                   .append("<a href=\"/file1\">file1</a>");

        assertEquals(builder.toString(), serializer.serialize(response));

    }

}
