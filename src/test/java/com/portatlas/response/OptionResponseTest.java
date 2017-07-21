package com.portatlas.response;

import com.portatlas.request.RequestMethod;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionResponseTest {

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, OptionResponse.run().getStatus());
    }

    @Test
    public void testResponseHeaderCanBeSet() {
        Response response = OptionResponse.run("Allow", "GET");

        assertEquals(RequestMethod.GET, response.getHeader("Allow"));
    }
}
