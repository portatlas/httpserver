package com.portatlas.response;

import java.io.IOException;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResponseSerializerTest {

    @Test
    public void testItSerializesResponseLine() throws IOException {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        byte[] responseWithStatusOKBytes = ResponseSerializer.serialize(response);
        String responseString = new String(responseWithStatusOKBytes);

        assertEquals("HTTP/1.1 200 OK" + ResponseSerializer.CRLF, responseString);
    }

    @Test
    public void testItSerializesHeaders() throws IOException {
        Response response = Response.builder()
                                    .header(HeaderName.CONTENT_TYPE, "text/plain")
                                    .header(HeaderName.CONTENT_LENGTH, "3")
                                    .build();

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(ResponseSerializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(ResponseSerializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(ResponseSerializer.CRLF);

        assertEquals(builder.toString(), Converter.bytesToString(ResponseSerializer.serialize(response)));
    }

    @Test
    public void testItSerializesBodyWithText() throws IOException {
        Response response = Response.builder()
                                    .header(HeaderName.CONTENT_TYPE, "text/plain")
                                    .header(HeaderName.CONTENT_LENGTH, "3")
                                    .body("<a href=\"/file1\">file1</a>".getBytes())
                                    .build();

        StringBuilder builder = new StringBuilder().append("HTTP/1.1 200 OK")
                                                   .append(ResponseSerializer.CRLF)
                                                   .append("Content-Length: 3")
                                                   .append(ResponseSerializer.CRLF)
                                                   .append("Content-Type: text/plain")
                                                   .append(ResponseSerializer.CRLF)
                                                   .append(ResponseSerializer.CRLF)
                                                   .append("<a href=\"/file1\">file1</a>");

        assertEquals(builder.toString(), Converter.bytesToString(ResponseSerializer.serialize(response)));
    }
}
