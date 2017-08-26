package com.portatlas.cobspec;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UseCookieResponseTest {
    private UseCookieResponse useCookieResponse;
    private Request request;

    @Before
    public void setUp() {
        request = new Request(RequestMethod.GET, CobspecResources.EAT_COOKIE, HttpVersion.CURRENT_VER);
        request.addHeader(HeaderName.COOKIE, "type=chocolate");
        useCookieResponse = new UseCookieResponse(request);
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, useCookieResponse.run().getStatus());
    }

    @Test
    public void testBodyHasCookieSet() throws Exception {
        assertEquals("mmmm chocolate", Converter.bytesToString(useCookieResponse.run().getBody()));
    }
}