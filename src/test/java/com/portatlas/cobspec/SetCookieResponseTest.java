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

public class SetCookieResponseTest {
    private SetCookieResponse setCookieResponse;
    private Request request;
    private String chocolateCookie = "type=chocolate";

    @Before
    public void setUp() {
        request = new Request(RequestMethod.GET, CobspecResources.COOKIE, chocolateCookie, HttpVersion.CURRENT_VER);
        setCookieResponse = new SetCookieResponse(request);
    }

    @Test
    public void testResponseHasStatus200() {
         assertEquals(StatusCodes.OK, setCookieResponse.run().getStatus());
    }

    @Test
    public void testBodyContentIsEat() {
        assertEquals("Eat", Converter.bytesToString(setCookieResponse.run().getBody()));
    }

    @Test
    public void testSetCookieWithCookieFromURL() {
        assertEquals(chocolateCookie, setCookieResponse.run().getHeaders().get(HeaderName.SET_COOKIE));
    }
}