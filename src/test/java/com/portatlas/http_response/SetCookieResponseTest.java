package com.portatlas.http_response;

import com.portatlas.Cookie;
import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetCookieResponseTest {
    private SetCookieResponse setCookieResponse;
    private Request request;
    private Cookie cookie;

    @Before
    public void setUp() {
        request = new Request(RequestMethod.GET, "/cookie?type=chocolate", HttpVersion.CURRENT_VER);
        cookie = new Cookie();
        setCookieResponse = new SetCookieResponse(request, cookie);
    }

    @Test
    public void testResponseHasStatus200() {
         assertEquals(StatusCodes.OK, setCookieResponse.run().getStatus());
    }

    @Test
    public void testBodyContentIsEat() throws Exception {
        assertEquals("Eat", Converter.bytesToString(setCookieResponse.run().getBody()));
    }

    @Test
    public void testParseCookieToSet() throws Exception {
        assertEquals("type=chocolate", setCookieResponse.parseCookie("/cookie?type=chocolate"));
    }

    @Test
    public void testSetCookieWithCookieFromURL() throws Exception {
        assertEquals("type=chocolate", setCookieResponse.run().getHeaders().get(HeaderName.SET_COOKIE));
    }

    @Test
    public void testCookieIsStored() throws Exception {
        setCookieResponse.setCookie("type=chocolate");

        assertEquals("chocolate", cookie.get("type"));
    }
}