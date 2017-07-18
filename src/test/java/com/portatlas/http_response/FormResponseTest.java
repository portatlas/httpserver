package com.portatlas.http_response;

import com.portatlas.Directory;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

public class FormResponseTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus200() {
        Request putFormRequest = new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER);
        putFormRequest.setBody("hello");
        Directory directory = new Directory(tempFolder.getRoot().getPath());
        FormResponse postResponse = new FormResponse(putFormRequest, directory);

        assertEquals(StatusCodes.OK, postResponse.run().getStatus());
    }

}
