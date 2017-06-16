package com.portatlas.request;

import com.portatlas.HttpVersion;
import com.portatlas.request.Request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {

    private Request request;
    private Request request1 = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

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
    public void testSetHTTPVersionToRequest() {
        request.setHttpVersion(HttpVersion.CURRENT_VER);

        assertEquals(HttpVersion.CURRENT_VER, request.getHttpVersion());
    }

    @Test
    public void testRequestHasAMethod() {
        assertEquals(RequestMethod.GET, request1.getMethod());
    }

    @Test
    public void testRequestHasARequestTarget() {
        assertEquals("/", request1.getRequestTarget());
    }

    @Test
    public void testRequestHasAHTTPVersion() {
        assertEquals(HttpVersion.CURRENT_VER, request1.getHttpVersion());
    }

    @Test
    public void testHeadersCanBeAddedAndHasHeaderFieldNameHostIsTrue() {
        request1.addHeader("Host", "en.wikipedia.org:8080");

        assertTrue(request1.getHeaders().containsKey("Host"));
    }

    @Test
    public void testRequestHasHeaderFieldValue() {
        request1.addHeader("Host", "en.wikipedia.org:8080");

        assertEquals("en.wikipedia.org:8080", request1.getHeaders().get("Host"));
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
