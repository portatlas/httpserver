package com.portatlas;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class CookieTest {

    @Test
    public void testCookieDefaultsWithoutAnyCookieStored() throws Exception {
        Cookie cookie = new Cookie();
        assertTrue(cookie.isEmpty());
    }

    @Test
    public void testStoreNewCookieInJar() throws Exception {
        Cookie cookie = new Cookie();
        cookie.addCookie("type", "chocolate");

        assertEquals("chocolate", cookie.get("type"));
    }
}