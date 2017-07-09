package com.portatlas.response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseSerializerTest {
    
    @Test
    public void testItSerializes200OK() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();

        ResponseSerializer serializer = new ResponseSerializer();

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", serializer.serialize(response));
    }

    @Test
    public void testItSerializes404NotFound() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_FOUND)
                                    .build();

        ResponseSerializer serializer = new ResponseSerializer();

        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", serializer.serialize(response));
    }

    @Test
    public void testItSerializesHeaders() {
        Response response = Response.builder()
                                    .header("Content-Type", "text/plain")
                                    .header("Content-Length", "3")
                                    .build();

        ResponseSerializer serializer = new ResponseSerializer();

        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 200 OK\r\n");
        builder.append("Content-Length: 3\r\n");
        builder.append("Content-Type: text/plain\r\n");
        builder.append("\r\n");

        assertEquals(builder.toString(), serializer.serialize(response));
    }

}
