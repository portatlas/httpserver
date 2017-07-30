package com.portatlas.request;

import com.portatlas.constants.HeaderName;
import com.portatlas.constants.HttpVersion;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RequestParserTest {
    private String requestWithoutBody = "GET / HTTP/1.1\n" +
                                        "Host: en.wikipedia.org:8080\n" +
                                        "Accept-Language: en-us,en:q=0.5\n";

    private String requestWithParams = "GET /test?type=chocolate HTTP/1.1\n" +
                                       "Host: en.wikipedia.org:8080\n" +
                                       "Accept-Language: en-us,en:q=0.5\n";

    private String encodedParams = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
    private String requestWithEncodedParams = "GET " + encodedParams + " HTTP/1.1\n" +
                                              "Host: en.wikipedia.org:8080\n" +
                                              "Accept-Language: en-us,en:q=0.5\n";

    private String requestWithBody = "POST /form HTTP/1.1\n" +
                                     "Content-Length: 11\n" +
                                     "Host: localhost:5000\n" +
                                     "Connection: Keep-Alive\n" +
                                     "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                                     "Accept-Encoding: gzip,deflate\n" +
                                     "\n" +
                                     "data=fatcat";

    private String headers = "Host: en.wikipedia.org:8080\n" +
                             "Accept-Language: en-us,en:q=0.5\n";

    private ByteArrayInputStream requestInputStream(byte[] request) {
        ByteArrayInputStream requestInputStream = new ByteArrayInputStream(request);
        return requestInputStream;
    }

    public Request parseRequest(byte[] request) throws IOException {
        return RequestParser.parseRequest(requestInputStream(request));
    }

    @Test
    public void testMethodsIsParsed() throws IOException {
        assertEquals(RequestMethod.GET, parseRequest(requestWithoutBody.getBytes()).getMethod());
    }

    @Test
    public void testRequestTargetIsParsed() throws IOException {
        assertEquals("/", parseRequest(requestWithoutBody.getBytes()).getRequestTarget());
    }

    @Test
    public void testRequestTargetWithParamsIsParsed() throws Exception {
        assertEquals("/test", parseRequest(requestWithParams.getBytes()).getRequestTarget());
    }

    @Test
    public void testParamsIsParsed() throws Exception {
        assertEquals("type = chocolate", parseRequest(requestWithParams.getBytes()).getRequestParams());
    }

    @Test
    public void testEncodedParamsIsParsed() throws Exception {
        String decodedParams = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        assertEquals(decodedParams, parseRequest(requestWithEncodedParams.getBytes()).getRequestParams());
    }

    @Test
    public void testHTTPVersionIsParsed() throws IOException {
        assertEquals(HttpVersion.CURRENT_VER, parseRequest(requestWithoutBody.getBytes()).getHttpVersion());
    }
    
    @Test
    public void testHeadersAreSlicedFromRequest() throws IOException {
        requestInputStream(requestWithoutBody.getBytes());
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(requestInputStream(requestWithoutBody.getBytes())));
        requestReader.readLine();

        assertEquals(headers, RequestParser.extractHeaders(requestReader));
    }

    @Test
    public void testHeadersAreParsed() throws IOException {
        Request request = parseRequest(requestWithoutBody.getBytes());
        RequestParser.parseHeaders(request, headers);

        assertEquals("en.wikipedia.org:8080", request.getHeaders().get(HeaderName.HOST));
        assertEquals("en-us,en:q=0.5", request.getHeaders().get(HeaderName.LANGUAGE));
    }

    @Test
    public void testBodyIsParsed() throws Exception {
        assertEquals("data=fatcat", parseRequest(requestWithBody.getBytes()).getRequestBody());
    }

    @Test
    public void testRequestLineAndHeadersAreParsed() throws IOException {
        Request parsedRequest = parseRequest(requestWithoutBody.getBytes());

        assertEquals(RequestMethod.GET, parsedRequest.getMethod());
        assertEquals("/", parsedRequest.getRequestTarget());
        assertEquals(HttpVersion.CURRENT_VER, parsedRequest.getHttpVersion());
        assertEquals("en.wikipedia.org:8080", parsedRequest.getHeaders().get(HeaderName.HOST));
        assertEquals("en-us,en:q=0.5", parsedRequest.getHeaders().get(HeaderName.LANGUAGE));
    }
}
