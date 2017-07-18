package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RedirectResponseTest {
    @Test
    public void testResponseHasStatus302() {
        RedirectResponse redirectResponse = new RedirectResponse();

        Assert.assertEquals(StatusCodes.FOUND, redirectResponse.run().getStatus());
    }

    @Test
    public void testResponseHasRedirectLocation() {
        RedirectResponse redirectResponse = new RedirectResponse();

        assertEquals("/", redirectResponse.run().getHeader("Location"));
    }
}
