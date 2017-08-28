package com.portatlas.cobspec;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.StatusCodes;

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
