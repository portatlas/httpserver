package com.portatlas.response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NotFoundResponseTest {

    @Test
    public void testResponseHasStatus404() {

        NotFoundResponse notFoundResponse = new NotFoundResponse();

        assertEquals(StatusCodes.NOT_FOUND , notFoundResponse.run().getStatus());
    }

}
