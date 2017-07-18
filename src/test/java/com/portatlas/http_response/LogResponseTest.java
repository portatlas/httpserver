package com.portatlas.http_response;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LogResponseTest {
    private LogResponse unauthLogResponse;
    private LogResponse authLogResponse;
    private Request unauthorizedRequest;
    private Request authorizedRequest;

    @Before
    public void setUp() {
        unauthorizedRequest = new Request(RequestMethod.GET, "/logs", HttpVersion.CURRENT_VER);
        authorizedRequest = new Request(RequestMethod.GET, "/logs", HttpVersion.CURRENT_VER);
        authorizedRequest.addHeader(HeaderName.AUTH, "Basic YWRtaW46aHVudGVyMg==");
        unauthLogResponse = new LogResponse(unauthorizedRequest);
        authLogResponse = new LogResponse(authorizedRequest);
    }

    @Test
    public void testUnAuthResponseHasStatus200() throws Exception {
        assertEquals(StatusCodes.OK, authLogResponse.run().getStatus());
    }

    @Test
    public void testResponseHasHeadersWithBasicAuth() {
        assertEquals("Basic", authLogResponse.run().getHeader(HeaderName.WWW_AUTH));
    }
}
