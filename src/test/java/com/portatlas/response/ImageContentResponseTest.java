package com.portatlas.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageContentResponseTest {

    @Test
    public void testResponseHasStatus200() {
        OkResponse okResponse = new OkResponse();

        assertEquals(StatusCodes.OK, okResponse.run().getStatus());
    }

}
