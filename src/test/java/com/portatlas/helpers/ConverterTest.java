package com.portatlas.helpers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConverterTest {

    @Test
    public void testConvertsByteArraysToString() throws Exception {
        byte[] bytes = "hello".getBytes();

        assertEquals("hello", Converter.bytesToString(bytes));
    }
}
