package com.portatlas.response;

import com.portatlas.Directory;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RootResponseTest {
    private RootResponse rootResponse;
    private ArrayList<String> files = new ArrayList<String>();
    private String filePath = System.getProperty("user.dir") + "/public/";
    private Directory directory = new Directory(filePath);

    @Before
    public void setUp() {
        rootResponse = new RootResponse();
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, rootResponse.run(files).getStatus());
    }
}
