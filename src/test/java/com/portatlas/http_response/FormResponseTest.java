package com.portatlas.http_response;

import com.portatlas.Directory;
import com.portatlas.constants.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.helpers.ResourceReader;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class FormResponseTest {
    private String directoryPath;
    private Directory dir;
    private FormResponse formResponse;
    private Request postFormRequest;
    private String content = "hello";

    @Before
    public void setUp() throws IOException {
        directoryPath = tempFolder.getRoot().getPath();
        dir = new Directory(directoryPath);
        postFormRequest = new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER);
        postFormRequest.setBody(content);
        formResponse = new FormResponse(postFormRequest, dir);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, formResponse.run().getStatus());
    }

    @Test
    public void testResponseWritesBodyContentToFile() {
        formResponse.run();

        assertEquals(content, Converter.bytesToString(ResourceReader.getContent(directoryPath + "/form")));
    }
}
