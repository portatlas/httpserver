package com.portatlas;

import java.io.IOException;
import java.net.ServerSocket;
import com.portatlas.mocks.MockServerSocket;
import com.portatlas.mocks.MockSocket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private MockSocket socket;
    private MockServerSocket serverSocket;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        socket = new MockSocket();
        serverSocket = new MockServerSocket();
    }

    @Test
    public void testCreateClientThreadCreatesANewClientThreadInstance() throws IOException {
        Directory directory = new Directory();
        ClientThread clientThread = new ClientThread(socket, directory);

        assertEquals(clientThread.getClass(), Server.createClientThread(serverSocket).getClass());
    }

    @Test
    public void testConfigureServerReturnsServerSocketWithArgsGiven() throws IOException {
        String[] args = new String[]{"-p", "7071", "-d", tempFolder.getRoot().getPath()};
        ServerSocket runningSocket = Server.configureServer(args);

        assertEquals(7071, runningSocket.getLocalPort());
    }

    @Test
    public void testServerSocketIsClosed() throws IOException {
        ServerSocket runningSocket = new MockServerSocket();
        Server.closeServerSocket(runningSocket);

        assertTrue(runningSocket.isClosed());
    }
}
