package com.portatlas.cobspec;

import com.portatlas.helpers.Converter;
import com.portatlas.helpers.http_helpers.ParameterDecoder;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.http_constants.StatusCodes;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParameterResponseTest {
    private Request request;
    private ParameterResponse parameterResponse;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        String decodedParams = ParameterDecoder.decode("variable_1=Operators%20%3C");
        request = new Request(StatusCodes.OK, "/target", decodedParams, HttpVersion.CURRENT_VER);
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
