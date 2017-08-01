package com.portatlas.http_response;

import com.portatlas.response.StatusCodes;

import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class DeleteResponseTest {
    private String directoryPath;
    private String file = "/tempfiletodelete";

    @Before
    public void setUp() throws IOException {
        directoryPath = tempFolder.getRoot().getPath();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus200() {
        DeleteResponse deleteResponse = new DeleteResponse(directoryPath, file);

        assertEquals(StatusCodes.OK, deleteResponse.run().getStatus());
    }
}