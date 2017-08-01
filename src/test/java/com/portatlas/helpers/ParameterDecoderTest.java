package com.portatlas.helpers;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParameterDecoderTest {
    @Test
    public void testDecodeParams() throws UnsupportedEncodingException {
        String params = "variable_1=Operators%20%3C";

        assertEquals("variable_1 = Operators <", ParameterDecoder.decode(params));
    }

    @Test
    public void testFormatParams() {
        String queryString = "variable_1=Operators%20%3C&variable_2=stuff";

        assertEquals("variable_1 = Operators%20%3C\nvariable_2 = stuff", ParameterDecoder.formatParams(queryString));
    }
}
