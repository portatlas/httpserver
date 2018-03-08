package com.portatlas.helpers.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CookieParserTest {
    private String rawCookie = "__atuvc=12%7C24; textwrapon=false; wysiwyg=textarea; _ga=GA1.1.2116285429.1499396819; type=chocolate";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReturnCookieFromProvidedKey() throws Exception {
        assertEquals("GA1.1.2116285429.1499396819",  CookieParser.getCookieFromKey(rawCookie,"_ga"));
        assertEquals("chocolate",  CookieParser.getCookieFromKey(rawCookie, "type"));
    }

    @Test
    public void testReturnsNullIfCookieDoesNotHaveKey() throws Exception {
        assertNull(CookieParser.getCookieFromKey(rawCookie, "noSuchKey"));
    }

    @Test
    public void testCookieStringThrowsErrorIfFormatIsBad() throws Exception {
        String badCookie = "typechocolate";

        thrown.expect(Exception.class);
        thrown.expectMessage("Invalid Cookie Format");
        CookieParser.getCookieFromKey(badCookie, "type");
    }
}
