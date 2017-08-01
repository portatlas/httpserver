package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NotFoundResponseTest {

    @Test
    public void testResponseHasStatus404() {
        NotFoundResponse notFoundResponse = new NotFoundResponse();

        assertEquals(StatusCodes.NOT_FOUND , notFoundResponse.run().getStatus());
    }
}
