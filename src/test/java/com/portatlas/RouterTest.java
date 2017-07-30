package com.portatlas;

import com.portatlas.constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_response.HttpResponse;
import com.portatlas.http_response.OkResponse;
import com.portatlas.response.StatusCodes;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RouterTest {
    private Router router;
    private Directory directory;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

    @Before
    public void setUp() throws IOException {
        directory = new Directory();
        router = new Router(directory);
        router.addStaticRoutes();
    }

    @Test
    public void testGivenAValidRequestRouteRespondsWithStatus200() {
        Request request = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
        HttpResponse httpResponse = new OkResponse();
        router.addRoute(request, httpResponse);

        HttpResponse routedResponse = router.route(request);

        assertEquals(httpResponse.run().getStatus(), routedResponse.run().getStatus());
    }

    @Test
    public void testHasRouteReturnsTrueWhenTheRequestExists() {
        Request request = new Request(RequestMethod.GET, "/anything" , HttpVersion.CURRENT_VER);
        HttpResponse httpResponse = new OkResponse();

        router.addRoute(request, httpResponse);

        assertTrue(router.hasRoute(request));
    }

    @Test
    public void testHasRouteReturnsFalseWhenRequestDoesNotExist() {
        Request request = new Request(RequestMethod.GET, "/foobar" , HttpVersion.CURRENT_VER);

        assertFalse(router.hasRoute(request));
    }

    @Test
    public void testAddRootRequestAndResponse() {
        assertEquals(StatusCodes.OK, router.route(getRootRequest).run().getStatus());
    }

    @Test
    public void testAddHeadRequestAndResponse() {
        Request headRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, router.route(headRequest).run().getStatus());
    }

    @Test
    public void testAddOptionsRequestAndResponse() {
        Request optionsRequest = new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, router.route(optionsRequest).run().getStatus());
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", router.route(optionsRequest).run().getHeader("Allow"));
    }

    @Test
    public void testAddOptions2RequestAndResponse() {
        Request options2Request = new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, router.route(options2Request).run().getStatus());
        assertEquals("GET,OPTIONS", router.route(options2Request).run().getHeader("Allow"));
    }

    @Test
    public void testAddGetFile1AndResponse() {
        Request getFile1Request = new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, router.route(getFile1Request).run().getStatus());
    }

    @Test
    public void testAddGetTextfiletxtAndResponse() {
        Request getTextfileTxtRequest = new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, router.route(getTextfileTxtRequest).run().getStatus());
    }

    @Test
    public void testAddGetRedirectRootAndResponse() {
        Request redirectRootRequest = new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.FOUND, router.route(redirectRootRequest).run().getStatus());
    }
}
