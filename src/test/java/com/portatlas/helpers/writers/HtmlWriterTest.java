package com.portatlas.helpers.writers;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HtmlWriterTest {
    private ArrayList<String> files = new ArrayList<String>();

    @Test
    public void testSetLinkForGivenFile() {
        files.add("file1");
        files.add("file2");

        assertEquals("<a href=\"/file1\">file1</a>\n<a href=\"/file2\">file2</a>\n", HtmlWriter.setLink(files));
    }
}
