package com.portatlas.helpers;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ContentRangeTest {
    private ContentRange contentRange;
    private int charLengthOfFile = 72;
    private int[] partialContentRange = new int[2];

    @Before
    public void setUp(){
        contentRange = new ContentRange();
    }

    @Test
    public void testParseRequestContentRangeWithStartAndEnd() {
        String contentRangeRequest = "0-4";
        partialContentRange[0] = 0;
        partialContentRange[1] = 5;

        assertTrue(Arrays.equals(partialContentRange, contentRange.getRange(contentRangeRequest, 2)));
    }

    @Test
    public void testParseRequestContentRangeWithJustEnd() {
        String contentRangeRequest = "-6";
        partialContentRange[0] = charLengthOfFile - 6;
        partialContentRange[1] = charLengthOfFile;

        assertTrue(Arrays.equals(partialContentRange, contentRange.getRange(contentRangeRequest, charLengthOfFile)));
    }

    @Test
    public void testParseRequestContentRangeWithStart() throws Exception {
        String contentRangeRequest = "4-";
        partialContentRange[0] = 4;
        partialContentRange[1] = charLengthOfFile;

        assertTrue(Arrays.equals(partialContentRange, contentRange.getRange(contentRangeRequest, charLengthOfFile)));
    }
}
