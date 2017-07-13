package com.portatlas.helpers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConverterTest {

    @Test
    public void testConvertsByteArraysToString() throws Exception {
        Converter convert = new Converter();
        byte[] bytes = "hello".getBytes();

        assertEquals("hello", convert.bytesToString(bytes));
    }
}
