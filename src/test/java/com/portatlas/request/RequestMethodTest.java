package com.portatlas.request;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RequestMethodTest {

    @Test
    public void testRequestMethods () {
        assertEquals("GET", RequestMethod.GET);
        assertEquals("HEAD", RequestMethod.HEAD);
        assertEquals("OPTIONS", RequestMethod.OPTIONS);
        assertEquals("POST", RequestMethod.POST);
        assertEquals("PUT", RequestMethod.PUT);
    }

}
