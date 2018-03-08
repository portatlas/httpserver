package com.portatlas.handler;

import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HeadHandlerTest {

    @Test
    public void testHeadRootRequestReturnsResponseWithStatus200() {
        Request headRootRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);
        HeadHandler rootHeadHandler = new HeadHandler(headRootRequest);

        assertEquals(StatusCodes.OK, rootHeadHandler.run().getStatus());
    }

    @Test
    public void testHeadBadRequestTargetReturnsResponseWithStatus404() {
        Request headUnknownResourceRequest = new Request(RequestMethod.HEAD, "/foobar" , HttpVersion.CURRENT_VER);
        HeadHandler unknownResourcetHeadHandler = new HeadHandler(headUnknownResourceRequest);

        assertEquals(StatusCodes.NOT_FOUND, unknownResourcetHeadHandler.run().getStatus());
    }
}
