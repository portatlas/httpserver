package com.portatlas.handler;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {
    private OptionsHandler optionsResponse;
    private Request methodOptionsRequest = new Request(RequestMethod.OPTIONS, "/method_options", HttpVersion.CURRENT_VER);

    @Before
    public void setUp() {
        optionsResponse = new OptionsHandler(methodOptionsRequest);
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, optionsResponse.run().getStatus());
    }

    @Test
    public void testOptionsRequestSetsAllowHeadersCorrectlyForMethodOption() throws Exception {
        Request methodOptionsRequest = new Request(RequestMethod.OPTIONS, "/method_options", HttpVersion.CURRENT_VER);

        assertEquals("GET,HEAD,POST,OPTIONS,PUT", optionsResponse.run().getHeader(HeaderName.ALLOW));
    }

    @Test
    public void testOptionsRequesDefaultValuesForAllowHeaders() {
        Request methodOptions2Request = new Request(RequestMethod.OPTIONS, "/method_options2", HttpVersion.CURRENT_VER);
        OptionsHandler methodOptions2Response = new OptionsHandler((methodOptions2Request));

        assertEquals("GET,OPTIONS", methodOptions2Response.run().getHeader(HeaderName.ALLOW));
    }
}
