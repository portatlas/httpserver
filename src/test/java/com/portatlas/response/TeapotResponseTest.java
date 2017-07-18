package com.portatlas.response;

import com.portatlas.helpers.Converter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TeapotResponseTest {
    TeapotResponse teapotResponse;

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