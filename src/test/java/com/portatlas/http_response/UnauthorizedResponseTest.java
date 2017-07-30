package com.portatlas.http_response;

import com.portatlas.constants.HeaderName;
import com.portatlas.helpers.Converter;
import com.portatlas.response.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnauthorizedResponseTest {
    private UnauthorizedResponse unauthResponse;

    @Before
    public void setUp() {
        unauthResponse = new UnauthorizedResponse();
    }

    @Test
    public void testResponseHasStatus401() {
        assertEquals(StatusCodes.UNAUTHORIZED, unauthResponse.run().getStatus());
    }

    @Test
    public void testHeadersContainBasicAuthentication() {
        assertEquals("Basic", unauthResponse.run().getHeader(HeaderName.WWW_AUTH));
    }

    @Test
    public void testBodyHasContent() {
        assertEquals("Request requires authentication", Converter.bytesToString(unauthResponse.run().getBody()));
    }
}