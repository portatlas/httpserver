package com.portatlas.handler;

import com.portatlas.http_constants.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MethodNotAllowedHandlerTest {
    @Test
    public void testResponseHasStatus405() {
        MethodNotAllowedHandler methodNotAllowedResponse = new MethodNotAllowedHandler();

        assertEquals(StatusCodes.NOT_ALLOWED , methodNotAllowedResponse.run().getStatus());
    }
}
