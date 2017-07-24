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
    public void testSpliceParametersAfterQuestionMark() throws Exception {
        String queryString = "/parameters?variable_1=Operators%20%3C";

        assertEquals("variable_1 = Operators%20%3C", ParameterDecoder.spliceForParams(queryString));
    }
}
