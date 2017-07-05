package com.portatlas.response;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RootResponseTest {
    private RootResponse rootResponse;
    private ArrayList<String> files = new ArrayList<String>();

    @Before
    public void setUp() {
        rootResponse = new RootResponse();
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, rootResponse.run(files).getStatus());
    }
}
