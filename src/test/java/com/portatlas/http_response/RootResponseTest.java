package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RootResponseTest {
    private RootResponse rootResponse;
    private ArrayList<String> files = new ArrayList<String>();



    @Before
    public void setUp() {
        rootResponse = new RootResponse(files);
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, rootResponse.run().getStatus());
    }
}
