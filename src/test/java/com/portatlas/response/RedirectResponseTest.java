package com.portatlas.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectResponseTest {

    private String redirectLocation = "/";

    @Test
    public void testResponseHasStatus302() {
        RedirectResponse redirectResponse = new RedirectResponse();

        assertEquals(StatusCodes.FOUND, redirectResponse.run(redirectLocation).getStatus());
    }

    @Test
    public void testResponseHasRedirectLocation() {
        RedirectResponse redirectResponse = new RedirectResponse();

        assertEquals(redirectLocation, redirectResponse.run(redirectLocation).getHeader("Location"));
    }

}
