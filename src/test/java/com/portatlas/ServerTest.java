package com.portatlas;

import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.StatusCodes;

import java.io.IOException;
import java.net.ServerSocket;
import com.portatlas.mocks.MockServerSocket;
import com.portatlas.mocks.MockSocket;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {
    private Server server;
    private String[] args = {"-p", "7070"};
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private MockSocket socket;
    private MockServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        server = new Server();
        server.addRoutes();
        socket = new MockSocket();
        serverSocket = new MockServerSocket();
    }

    @Test
    public void testCreateClientThreadCreatesANewClientThreadInstance() throws IOException {
        Directory directory = new Directory();
        Router router = new Router();
        ClientThread clientThread = new ClientThread(socket, router, directory);

        assertEquals(clientThread.getClass(), Server.createClientThread(serverSocket).getClass());
    }

    @Test
    public void testConfigureServerReturnsServerSocketWithArgsGiven() throws IOException {
        String[] args = {"-p", "7071"};
        ServerSocket runningSocket = Server.configureServer(args);

        assertEquals(7071, runningSocket.getLocalPort());
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
        ServerSocket runningSocket = new MockServerSocket();
        Server.closeServerSocket(runningSocket);

        assertTrue(runningSocket.isClosed());
    }
}
