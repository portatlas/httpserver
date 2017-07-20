package com.portatlas;

import com.portatlas.helpers.Converter;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.ResponseSerializer;
import com.portatlas.response.StatusCodes;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {
    private Server server;
    private Converter convert;
    private String[] args = {"-p", "7070"};
    private ResponseSerializer serializer;
    private ServerSocket serverSocket;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);

    @Before
    public void setUp() throws IOException {
        server = new Server();
        server.addRoutes();
        serializer = new ResponseSerializer();
        convert = new Converter();
    }

    @Test
    public void testConfigureServerReturnsServerSocketWithArgsGiven() throws IOException {
        ServerSocket runningSocket = server.configureServer(args);

        assertEquals(7070, runningSocket.getLocalPort());
    }

    @Test
    public void testAddRootRequestAndResponse() {
        assertEquals(StatusCodes.OK, server.router.route(getRootRequest).run().getStatus());
    }

    @Test
    public void testAddHeadRequestAndResponse() {
        Request headRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(headRequest).run().getStatus());
    }

    @Test
    public void testAddOptionsRequestAndResponse() {
        Request optionsRequest = new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(optionsRequest).run().getStatus());
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", server.router.route(optionsRequest).run().getHeader("Allow"));
    }

    @Test
    public void testAddOptions2RequestAndResponse() {
        Request options2Request = new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(options2Request).run().getStatus());
        assertEquals("GET,OPTIONS", server.router.route(options2Request).run().getHeader("Allow"));
    }

    @Test
    public void testAddPostRequestAndResponse() {
        Request postRequest = new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(postRequest).run().getStatus());
    }

    @Test
    public void testAddPuttRequestAndResponse() {
        Request putRequest = new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(putRequest).run().getStatus());
    }

    @Test
    public void testAddGetFile1AndResponse() {
        Request getFile1Request = new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(getFile1Request).run().getStatus());
    }

    @Test
    public void testAddGetTextfiletxtAndResponse() {
        Request getTextfileTxtRequest = new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, server.router.route(getTextfileTxtRequest).run().getStatus());
    }

    @Test
    public void testAddGetRedirectRootAndResponse() {
        Request redirectRootRequest = new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.FOUND, server.router.route(redirectRootRequest).run().getStatus());
    }

    @Test
    public void testServerSocketIsClosed() throws Exception {
        ServerSocket runningSocket = Server.configureServer(args);
        Server.closeServerSocket(runningSocket);

        assertTrue(runningSocket.isClosed());
    }
}
