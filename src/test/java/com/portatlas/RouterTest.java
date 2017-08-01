package com.portatlas;

import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_response.HttpResponse;
import com.portatlas.http_response.OkResponse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RouterTest {
    private Router router;

    @Before
    public void setUp() {
        router = new Router();
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
}
