package com.portatlas.request;

import com.portatlas.constants.HeaderName;
import com.portatlas.constants.HttpVersion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {
    private String chocolateCookie = "type=chocolate";
    private Request request;
    private Request request1 = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private Request request2 = new Request(RequestMethod.GET, "/" , chocolateCookie, HttpVersion.CURRENT_VER);

    @Before
    public void setUp() {
        request = new Request();
    }

    @Test
    public void testSetMethodToRequest() {
        request.setMethod(RequestMethod.GET);

        assertEquals(RequestMethod.GET, request.getMethod());
    }

    @Test
    public void testSetRequestTargetToRequest() {
        request.setRequestTarget("/foobar");

        assertEquals("/foobar", request.getRequestTarget());
    }

    @Test
    public void testSetRequestParamsToRequest() {
        request.setRequestParams(chocolateCookie);

        assertEquals(chocolateCookie, request.getRequestParams());
    }

    @Test
    public void testSetHTTPVersionToRequest() {
        request.setHttpVersion(HttpVersion.CURRENT_VER);

        assertEquals(HttpVersion.CURRENT_VER, request.getHttpVersion());
    }

    @Test
    public void testGetRequestMethod() {
        assertEquals(RequestMethod.GET, request1.getMethod());
    }

    @Test
    public void testGetRequestTarget() {
        assertEquals("/", request1.getRequestTarget());
    }

    @Test
    public void testGetParams() {
        assertEquals(chocolateCookie, request2.getRequestParams());
    }

    @Test
    public void testGetResourceFromRequestTarget() {
        request.setRequestTarget("/file.txt");

        assertEquals("file.txt", request.getResource());
    }

    @Test
    public void testRequestHasAHTTPVersion() {
        assertEquals(HttpVersion.CURRENT_VER, request1.getHttpVersion());
    }

    @Test
    public void testHeadersCanBeAddedAndHasHeaderFieldNameHostIsTrue() {
        request1.addHeader(HeaderName.HOST, "en.wikipedia.org:8080");

        assertTrue(request1.getHeaders().containsKey(HeaderName.HOST));
    }

    @Test
    public void testRequestHasHeaderFieldValue() {
        request1.addHeader(HeaderName.HOST, "en.wikipedia.org:8080");

        assertEquals("en.wikipedia.org:8080", request1.getHeaders().get(HeaderName.HOST));
    }

    @Test
    public void testRequestHasABody() {
        request1.setBody("data");

        assertEquals("data", request1.getRequestBody());
    }

    @Test
    public void testOverrideEqualsForRequest() {
        Request request2 = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

        assertEquals(request1, request2);
    }

    @Test
    public void testOverrideEqualsForMethodAndRequestTargetAndHTTPVersion() {
        assertEquals(RequestMethod.GET, request1.getMethod() );
        assertEquals("/", request1.getRequestTarget() );
        assertEquals(HttpVersion.CURRENT_VER, request1.getHttpVersion() );
    }

    @Test
    public void testOverideHashCodeForRequest() {
        Request request2 = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
