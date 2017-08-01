package com.portatlas;

import com.portatlas.constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import java.io.IOException;
import java.net.ServerSocket;
import com.portatlas.mocks.MockServerSocket;
import com.portatlas.mocks.MockSocket;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private MockSocket socket;
    private MockServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        server = new Server();
        socket = new MockSocket();
        serverSocket = new MockServerSocket();
    }

    @Test
    public void testCreateClientThreadCreatesANewClientThreadInstance() throws IOException {
        Directory directory = new Directory();
        Router router = new Router(directory);
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
    public void testServerSocketIsClosed() throws Exception {
        ServerSocket runningSocket = new MockServerSocket();
        Server.closeServerSocket(runningSocket);

        assertTrue(runningSocket.isClosed());
    }
}
