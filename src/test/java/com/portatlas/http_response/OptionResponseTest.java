package com.portatlas.http_response;

import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionResponseTest {
    private OptionResponse optionResponse;

    @Before
    public void setUp(){
        optionResponse = new OptionResponse("Allow", "GET");
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, optionResponse.run().getStatus());
    }

    @Test
    public void testResponseHeaderCanBeSet() {
        Response response = optionResponse.run();

        assertEquals(RequestMethod.GET, response.getHeader("Allow"));
    }
}
