package com.portatlas.response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StatusCodesTest {

    @Test
    public void testStatusOKis200() {
        assertEquals("200 OK", StatusCodes.OK);
    }

    @Test
    public void testStatusNotFoundis404() {
        assertEquals("404 Not Found", StatusCodes.NOT_FOUND);
    }

}
