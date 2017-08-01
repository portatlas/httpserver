package com.portatlas.helpers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ShaEncoderTest {

    @Test
    public void testEncodesTextToSHA1() {
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", ShaEncoder.encode("default content"));
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", ShaEncoder.encode("patched content"));
    }
}