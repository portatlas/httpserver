package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.helpers.readers.ResourceReader;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import java.io.File;
import java.io.IOException;

import com.portatlas.test_helpers.FileHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class PostPutHandlerTest {
    private String tempFolderPath;
    private Directory dir;
    private PostPutHandler formResponse;
    private Request postFormRequest;
    private String content = "hello";

    @Before
    public void setUp() throws IOException {
        tempFolderPath = tempFolder.getRoot().getPath();
        dir = new Directory(tempFolderPath);
        postFormRequest = new Request(RequestMethod.POST, "/new_form" , HttpVersion.CURRENT_VER);
        postFormRequest.setBody(content);
        formResponse = new PostPutHandler(postFormRequest, dir);
        FileHelper.createTempFileWithContent(tempFolder);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus200() {
        assertEquals(StatusCodes.OK, formResponse.run().getStatus());
    }

    @Test
    public void testResponseWritesBodyContentToFile() throws Exception {
        formResponse.run();

        assertEquals(content, Converter.bytesToString(ResourceReader.getContent(tempFolderPath + "/new_form")));
    }

    @Test
    public void testResponseHasStatus405IfRequestMethodIsNotAllowed() {
        Request postForCobspecResourceRequest = new Request(RequestMethod.POST, "/test.jpeg" , HttpVersion.CURRENT_VER);
        PostPutHandler methodNotAllowedResponse = new PostPutHandler(postForCobspecResourceRequest, dir);
        dir.listFilesForFolder(new File(tempFolderPath));

        assertEquals(StatusCodes.NOT_ALLOWED, methodNotAllowedResponse.run().getStatus());
    }
}
