package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.ResponseSerializer;
import com.portatlas.response.StatusCodes;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private ResponseSerializer serializer;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

    @Before
    public void setUp() {
        server = new Server();
        server.addRoutes();
        serializer = new ResponseSerializer();
    }

    @Test
    public void testConfigureServerReturnsServerSocketWithArgsGiven() throws IOException {
        String[] args = {"-p", "7070"};
        ServerSocket runningSocket = server.configureServer(args);

        assertEquals(7070, runningSocket.getLocalPort());
    }

    @Test
    public void testAddRootRequestAndResponse() {

        assertEquals(StatusCodes.OK, server.router.route(getRootRequest).getStatus());
    }

    @Test
    public void testAddHeadRequestAndResponse() {
        Request headRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(headRequest).getStatus());
    }

    @Test
    public void testAddOptionsRequestAndResponse() {
        Request optionsRequest = new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(optionsRequest).getStatus());
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", server.router.route(optionsRequest).getHeader("Allow"));
    }

    @Test
    public void testAddOptions2RequestAndResponse() {
        Request options2Request = new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(options2Request).getStatus());
        assertEquals("GET,OPTIONS", server.router.route(options2Request).getHeader("Allow"));
    }

    @Test
    public void testAddPostRequestAndResponse() {
        Request postRequest = new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(postRequest).getStatus());
    }

    @Test
    public void testAddPuttRequestAndResponse() {
        Request putRequest = new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(putRequest).getStatus());
    }
    
    @Test
    public void testAddGetFile1AndResponse() {
        Request getFile1Request = new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(getFile1Request).getStatus());
    }

    @Test
    public void testAddGetTextfiletxtAndResponse() {
        Request getTextfileTxtRequest = new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(getTextfileTxtRequest).getStatus());
    }

    @Test
    public void testAddGetRedirectRootAndResponse() {
        Request redirectRootRequest = new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.FOUND, server.router.route(redirectRootRequest).getStatus());
    }

    @Test
    public void testGetRootReturnsResponseWithStatus200() {
        StringBuilder responseString = new StringBuilder().append("HTTP/1.1 200 OK")
                                                          .append(serializer.CRLF)
                                                          .append(serializer.CRLF);

        assertTrue(server.handleRequest(getRootRequest).contains(responseString));
    }

    @Test
    public void testGetRootRedirectReturnsResponseWithStatus302() {
        Request getRootRedirectRequest = new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER);
        StringBuilder responseString = new StringBuilder().append("HTTP/1.1 302 Found")
                                                          .append(serializer.CRLF)
                                                          .append("Location: /")
                                                          .append(serializer.CRLF);

        assertEquals(responseString.toString(), server.handleRequest(getRootRedirectRequest));
    }

    @Test
    public void testHeadFoobarRequestReturnsResponseWithStatusNotFound() {
        Request getHeadFoobarRequest = new Request(RequestMethod.HEAD, "/foobar" , HttpVersion.CURRENT_VER);
        StringBuilder responseString = new StringBuilder().append("HTTP/1.1 404 Not Found")
                                                          .append(serializer.CRLF);

        assertEquals(responseString.toString(), server.handleRequest(getHeadFoobarRequest));
    }
    
    @Test
    public void testPutFileRequestReturnsResponseWithStatusMethodNotAllowed() {
        Request putFileRequest = new Request(RequestMethod.PUT, "/file1" , HttpVersion.CURRENT_VER);
        StringBuilder responseString = new StringBuilder().append("HTTP/1.1 405 Method Not Allowed")
                                                          .append(serializer.CRLF);

        assertEquals(responseString.toString(), server.handleRequest(putFileRequest));
    }
}
