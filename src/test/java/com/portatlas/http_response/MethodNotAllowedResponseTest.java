package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MethodNotAllowedResponseTest {

    @Test
    public void testResponseHasStatus405() {
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse();

        assertEquals(StatusCodes.NOT_ALLOWED , methodNotAllowedResponse.run().getStatus());
    }
}
