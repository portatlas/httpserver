package com.portatlas.http_response;

import com.portatlas.Cookie;
import com.portatlas.helpers.Converter;
import com.portatlas.response.StatusCodes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UseCookieResponseTest {

    private Cookie cookie;
    private UseCookieResponse useCookieResponse;

    @Before
    public void setUp() {
        cookie = new Cookie();
        cookie.addCookie("type", "chocolate");
        useCookieResponse = new UseCookieResponse(cookie);
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