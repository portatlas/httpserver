package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.StatusCodes;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {
    private DirectoryHandler rootResponse;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        tempFolder.newFile("file1");
        tempFolder.newFile("file2");
        Directory directory = new Directory(tempFolder.getRoot().getPath());
        rootResponse = new DirectoryHandler(directory);
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