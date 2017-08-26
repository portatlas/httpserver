package com.portatlas;

import com.portatlas.cobspec.CobspecResources;
import com.portatlas.handler.GetHandler;
import com.portatlas.handler.HeadHandler;
import com.portatlas.handler.OptionsHandler;
import com.portatlas.handler.PostPutHandler;
import com.portatlas.handler.PatchHandler;
import com.portatlas.handler.DeleteHandler;
import com.portatlas.handler.MethodNotAllowedHandler;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.http_constants.StatusCodes;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.test_helpers.FileHelper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

public class ControllerTest {
    private Controller controller;
    private Directory directory;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private String okResponse = "HTTP/1.1 200 OK";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        controller = new Controller();
        FileHelper.createTempFileWithContent(tempFolder);
        directory = new Directory(tempFolder.getRoot().getPath());
    }

    @Test
    public void testInvalidRequestMethodNotAllowedHandler() throws IOException {
        Request invalidMethodRequest = new Request("boogus", CobspecResources.FORM , HttpVersion.CURRENT_VER);

        assertEquals(new MethodNotAllowedHandler().getClass(), controller.routeToHandler(invalidMethodRequest, directory).getClass());
    }

    @Test
    public void testValidGetRequestReturnsGetHandler() throws IOException {
        assertEquals(new GetHandler(getRootRequest, directory).getClass(), controller.routeToHandler(getRootRequest, directory).getClass());
    }

    @Test
    public void testValidHeadRequestReturnsHeadHandler() throws IOException {
        Request headRootRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);

        assertEquals(new HeadHandler(headRootRequest).getClass(), controller.routeToHandler(headRootRequest, directory).getClass());
    }

    @Test
    public void testValidOptionsRequestReturnsOptionsHandler() throws IOException {
        Request optionsRequest = new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER);

        assertEquals(new OptionsHandler(optionsRequest).getClass(), controller.routeToHandler(optionsRequest, directory).getClass());
    }

    @Test
    public void testValidPostRequestReturnsPostPutHandler() throws IOException {
        Request postRequest = new Request(RequestMethod.POST, CobspecResources.FORM , HttpVersion.CURRENT_VER);

        assertEquals(new PostPutHandler(postRequest, directory).getClass(), controller.routeToHandler(postRequest, directory).getClass());
    }

    @Test
    public void testValidPutRequestReturnsPostPutHandler() throws IOException {
        Request postRequest = new Request(RequestMethod.PUT, CobspecResources.FORM , HttpVersion.CURRENT_VER);

        assertEquals(new PostPutHandler(postRequest, directory).getClass(), controller.routeToHandler(postRequest, directory).getClass());
    }

    @Test
    public void testValidPatchtRequestReturnsPatchHandler() throws IOException {
        Request patchRequest = new Request(RequestMethod.PATCH, CobspecResources.FORM , HttpVersion.CURRENT_VER);

        assertEquals(new PatchHandler(patchRequest, directory).getClass(), controller.routeToHandler(patchRequest, directory).getClass());
    }

    @Test
    public void testValidDeleteRequestReturnsDeleteHandler() throws IOException {
        Request deleteRequest = new Request(RequestMethod.DELETE, CobspecResources.FORM , HttpVersion.CURRENT_VER);

        assertEquals(new DeleteHandler(deleteRequest, directory).getClass(), controller.routeToHandler(deleteRequest, directory).getClass());
    }

    @Test
    public void testProcessRequestReturnsResponse() throws IOException {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        assertEquals(response.getClass(), controller.processRequest(getRootRequest, directory).getClass());
    }
}
