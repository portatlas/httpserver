package com.portatlas.http_response;

import com.portatlas.constants.HeaderName;
import com.portatlas.response.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RedirectResponseTest {
    @Test
    public void testResponseHasStatus302() {
        RedirectResponse redirectResponse = new RedirectResponse();

        assertEquals(StatusCodes.FOUND, redirectResponse.run().getStatus());
    }

    @Test
    public void testResponseHasRedirectLocation() {
        RedirectResponse redirectResponse = new RedirectResponse();

        assertEquals("/", redirectResponse.run().getHeader(HeaderName.LOCATION));
    }
}
