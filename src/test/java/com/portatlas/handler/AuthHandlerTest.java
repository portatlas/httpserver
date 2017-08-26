package com.portatlas.handler;

import com.portatlas.cobspec.CobspecResources;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AuthHandlerTest {
    private AuthHandler authLogResponse;
    private Request authorizedRequest;

    @Before
    public void setUp() {
        authorizedRequest = new Request(RequestMethod.GET, CobspecResources.LOGS, HttpVersion.CURRENT_VER);
        authorizedRequest.addHeader(HeaderName.AUTH, "Basic YWRtaW46aHVudGVyMg==");
        authLogResponse = new AuthHandler(authorizedRequest);
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
