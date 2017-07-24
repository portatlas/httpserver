package com.portatlas.http_response;

import com.portatlas.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.request.Request;
import com.portatlas.response.StatusCodes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterResponseTest {
    private Request request;
    private ParameterResponse parameterResponse;

    @Before
    public void setUp() {
        request = new Request(StatusCodes.OK, "/target?variable_1=Operators%20%3C", HttpVersion.CURRENT_VER);
        parameterResponse = new ParameterResponse(request);
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, parameterResponse.run().getStatus());
    }

    @Test
    public void testResponseHasBodyOfParams() {
        assertEquals("variable_1 = Operators <", Converter.bytesToString(parameterResponse.run().getBody()));
    }
}
