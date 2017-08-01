package com.portatlas.helpers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class CookieParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCookieStringIsParsed() throws Exception {
        String rawCookie = "__atuvc=12%7C24; textwrapon=false; wysiwyg=textarea; _ga=GA1.1.2116285429.1499396819; type=chocolate";

        CookieParser.parseCookies(rawCookie);

        assertEquals("GA1.1.2116285429.1499396819",  CookieParser.getCookie("_ga"));
        assertEquals("chocolate",  CookieParser.getCookie("type"));
    }

    @Test
    public void testCookieStringThrowsErrorIfFormatIsBad() throws Exception {
        String rawCookie = "chocolate";

        thrown.expect(Exception.class);
        thrown.expectMessage("Invalid Cookie Format");
        CookieParser.parseCookies(rawCookie);
    }
}
