package com.portatlas.helpers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConverterTest {

    @Test
    public void testConvertsByteArraysToString() {
        byte[] bytes = "hello".getBytes();

        assertEquals("hello", Converter.bytesToString(bytes));
    }

    @Test
    public void testConvertBytesToHex() {
        byte[] bytes = "0".getBytes();

        assertEquals("30", Converter.bytesToHex(bytes));
    }
}
