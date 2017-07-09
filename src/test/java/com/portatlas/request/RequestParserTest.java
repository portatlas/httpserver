package com.portatlas.request;

import com.portatlas.HttpVersion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestParserTest {

    private RequestParser parser;
    private String sampleRequest = "GET / HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
    private String headers = "Host: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
    private ByteArrayInputStream sampleRequestInputStream = new ByteArrayInputStream(sampleRequest.getBytes());

    @Before
    public void setUp() throws Exception {
        parser = new RequestParser();
    }

    public Request parseRequest() throws IOException {
        return parser.parseRequest(sampleRequestInputStream);
    }

    @Test
    public void testMethodsIsParsed() throws IOException {
        assertEquals(RequestMethod.GET, parseRequest().getMethod());
    }

    @Test
    public void testRequestTargetIsParsed() throws IOException {
        assertEquals("/", parseRequest().getRequestTarget());
    }

    @Test
    public void testHTTPVersionIsParsed() throws IOException {
        assertEquals(HttpVersion.CURRENT_VER, parseRequest().getHttpVersion());
    }
    
    @Test
    public void testHeadersAreSlicedFromRequest() throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(sampleRequestInputStream));
        requestReader.readLine();

        assertEquals(headers, parser.extractHeaders(requestReader));
    }

    @Test
    public void testHeadersAreParsed() throws IOException {
        Request request = parseRequest();
        parser.parseHeaders(request, headers);

        assertEquals("en.wikipedia.org:8080", request.getHeaders().get("Host"));
        assertEquals("en-us,en:q=0.5", request.getHeaders().get("Accept-Language"));
    }

    @Test
    public void testParsesRequestLineAndHeader() throws IOException {
        Request parsedRequest = parseRequest();

        assertEquals(RequestMethod.GET, parsedRequest.getMethod());
        assertEquals("/", parsedRequest.getRequestTarget());
        assertEquals(HttpVersion.CURRENT_VER, parsedRequest.getHttpVersion());
        assertEquals("en.wikipedia.org:8080", parsedRequest.getHeaders().get("Host"));
        assertEquals("en-us,en:q=0.5", parsedRequest.getHeaders().get("Accept-Language"));
    }

}
