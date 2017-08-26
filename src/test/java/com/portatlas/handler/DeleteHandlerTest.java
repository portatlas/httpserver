package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeleteHandlerTest {
    private String tempFolderPath;
    private Directory directory;
    private String file = "tempfiletodelete";
    private Request deleteRequest = new Request(RequestMethod.DELETE, "/tempfiletodelete", HttpVersion.CURRENT_VER);

    @Before
    public void setUp() throws IOException {
        tempFolderPath = tempFolder.getRoot().getPath();
        directory = new Directory(tempFolderPath);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testResponseHasStatus200() throws Exception {
        DeleteHandler deleteResponse = new DeleteHandler(deleteRequest, directory);
        tempFolder.newFile(file);
        directory.listFilesForFolder(new File(tempFolderPath));

        assertEquals(StatusCodes.OK, deleteResponse.run().getStatus());
    }

    @Test
    public void testFileHasBeenDeleted() throws IOException {
        tempFolder.newFile(file);
        DeleteHandler deleteResponse = new DeleteHandler(deleteRequest, directory);
        deleteResponse.run();

        assertFalse(directory.hasFile(file));
    }

    @Test
    public void testResponseHasStatus404IfFileDoesNotExist() {
        Request deleteMissingResourceRequest = new Request(RequestMethod.DELETE, "/filedoesnotexist", HttpVersion.CURRENT_VER);
        DeleteHandler deleteResponse = new DeleteHandler(deleteMissingResourceRequest, directory);

        assertEquals(StatusCodes.NOT_FOUND, deleteResponse.run().getStatus());
    }
}
