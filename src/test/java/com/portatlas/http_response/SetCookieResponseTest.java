package com.portatlas.http_response;

import com.portatlas.helpers.Converter;
import com.portatlas.constants.HeaderName;
import com.portatlas.constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SetCookieResponseTest {
    private SetCookieResponse setCookieResponse;
    private Request request;
    private String chocolateCookie = "type=chocolate";
    @Before
    public void setUp() {
        request = new Request(RequestMethod.GET, "/cookie", chocolateCookie, HttpVersion.CURRENT_VER);
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