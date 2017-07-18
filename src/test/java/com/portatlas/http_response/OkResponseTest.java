package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OkResponseTest {

    @Test
    public void testResponseHasStatus200() {
        OkResponse okResponse = new OkResponse();

        assertEquals(StatusCodes.OK, okResponse.run().getStatus());
    }
}
