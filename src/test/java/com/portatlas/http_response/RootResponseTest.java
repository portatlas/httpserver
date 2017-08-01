package com.portatlas.http_response;

import com.portatlas.helpers.Converter;
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
        files.add("file1");
        files.add("file2");
        rootResponse = new RootResponse(files);
    }

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, rootResponse.run().getStatus());
    }

    @Test
    public void testBodyHasLinksOfFiles() {
        String expectedHtmlTag = "<a href=\"/file1\">file1</a>\n<a href=\"/file2\">file2</a>\n";
        assertEquals(expectedHtmlTag, Converter.bytesToString(rootResponse.run().getBody()));
    }
}
