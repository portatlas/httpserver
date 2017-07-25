package com.portatlas;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_response.RootResponse;
import com.portatlas.http_response.RedirectResponse;
import com.portatlas.http_response.FormResponse;
import com.portatlas.response.ResponseSerializer;
import com.portatlas.test_helpers.FileHelper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ControllerTest {
    private Controller controller;
    private Router router;
    private Directory directory;
    private Request testRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        controller = new Controller();
        FileHelper.createTempFileWithContent(tempFolder);
        directory = new Directory(tempFolder.getRoot().getPath());
        router = new Router(directory);
        testRequest.setBody("hello");
        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER),
                        new RootResponse(directory.files));
        router.addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER),
                        new RedirectResponse());
        router.addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER),
                        new FormResponse(testRequest, directory));
    }

    @Test
    public void testBadAuthRequest() throws Exception {
        Request badAuthrequest = new Request(RequestMethod.GET, "/logs", HttpVersion.CURRENT_VER);
        badAuthrequest.addHeader(HeaderName.AUTH, "Basic 111taW46aHVudGVyMg==");
        StringBuilder unauthResponse = new StringBuilder();
        unauthResponse.append("HTTP/1.1 401 Unauthorized")
                      .append(ResponseSerializer.CRLF)
                      .append("WWW-Authenticate: Basic");

        assertTrue(Converter.bytesToString(controller.handleRequest(badAuthrequest, router, directory)).contains(unauthResponse.toString()));
    }

    @Test
    public void testGoodAuthRequest() throws Exception {
        Request badAuthrequest = new Request(RequestMethod.GET, "/logs", HttpVersion.CURRENT_VER);
        badAuthrequest.addHeader(HeaderName.AUTH, "Basic YWRtaW46aHVudGVyMg==");
        StringBuilder authResponse = new StringBuilder();
        authResponse.append("HTTP/1.1 200 OK")
                    .append(ResponseSerializer.CRLF)
                    .append("WWW-Authenticate: Basic");

        assertTrue(Converter.bytesToString(controller.handleRequest(badAuthrequest, router, directory)).contains(authResponse.toString()));
    }

    @Test
    public void testGetRootReturnsResponseWithStatus200() throws IOException {
        StringBuilder responseWithStatusOK = new StringBuilder();
        responseWithStatusOK.append("HTTP/1.1 200 OK")
                            .append(ResponseSerializer.CRLF);

        assertTrue(Converter.bytesToString(controller.handleRequest(testRequest, router, directory)).contains(responseWithStatusOK.toString()));
    }

    @Test
    public void testGetRootRedirectReturnsResponseWithStatus302() throws IOException {
        Request getRootRedirectRequest = new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER);

        StringBuilder responseWithStatusFound = new StringBuilder();
        responseWithStatusFound.append("HTTP/1.1 302 Found")
                               .append(ResponseSerializer.CRLF)
                               .append("Location: /")
                               .append(ResponseSerializer.CRLF);

        assertEquals(responseWithStatusFound.toString(), Converter.bytesToString(controller.handleRequest(getRootRedirectRequest, router, directory)));
    }

    @Test
    public void testHeadFoobarRequestReturnsResponseWithStatusNotFound() throws IOException {
        Request getHeadFoobarRequest = new Request(RequestMethod.HEAD, "/foobar" , HttpVersion.CURRENT_VER);

        StringBuilder responseWithStatusNotFound = new StringBuilder();
        responseWithStatusNotFound.append("HTTP/1.1 404 Not Found")
                                  .append(ResponseSerializer.CRLF);

        assertEquals(responseWithStatusNotFound.toString(), Converter.bytesToString(controller.handleRequest(getHeadFoobarRequest, router, directory)));
    }

    @Test
    public void testPutFileRequestReturnsResponseWithStatusMethodNotAllowed() throws IOException {
        Request putFileRequest = new Request(RequestMethod.PUT, "/test_temp_file" , HttpVersion.CURRENT_VER);

        StringBuilder responseWithStatusNotAllowed = new StringBuilder();
        responseWithStatusNotAllowed.append("HTTP/1.1 405 Method Not Allowed")
                                    .append(ResponseSerializer.CRLF);

        assertEquals(responseWithStatusNotAllowed.toString(), Converter.bytesToString(controller.handleRequest(putFileRequest, router, directory)));
    }
}
