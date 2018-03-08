package com.portatlas.cobspec;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TeapotResponseTest {
    private TeapotResponse teapotResponse;

    @Before
    public void setUp() {
        teapotResponse = new TeapotResponse();
    }

    @Test
    public void testResponseHasStatus418() {
        assertEquals(StatusCodes.TEA_POT, teapotResponse.run().getStatus());
    }

    @Test
    public void testResponseHasBody() {
        assertEquals("I'm a teapot", Converter.bytesToString(teapotResponse.run().getBody()));
    }
}
