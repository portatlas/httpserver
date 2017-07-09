package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

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
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        router.addRoute(request, response);
        Response routedResponse = router.route(request);

        assertEquals(response, routedResponse);
        assertEquals(response.getStatus(), routedResponse.getStatus());
    }

    @Test
    public void testHasRouteReturnsTrueWhenRequestExist() {
        Request request = new Request(RequestMethod.GET, "/anything" , HttpVersion.CURRENT_VER);
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        router.addRoute(request, response);

        assertTrue(router.hasRoute(request));
    }

    @Test
    public void testHasRouteReturnsFalseWhenRequestDoesNotExist() {
        Request request = new Request(RequestMethod.GET, "/foobar" , HttpVersion.CURRENT_VER);

        assertFalse(router.hasRoute(request));
    }

}
