package com.portatlas;

import com.portatlas.request.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientThreadTest {
    private Socket socket;
    private Directory directory;
    private Router router;
    private ClientThread clientThread;

    @Before
    public void setUp() throws IOException {
        socket = new Socket();
        directory = new Directory();
        router = new Router();
        clientThread = new ClientThread(socket, router, directory);
    }

    @Test
    public void testBuildRequestFromInputStream() throws Exception {
        String sampleRequest = "GET / HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
        ByteArrayInputStream sampleRequestInputStream = new ByteArrayInputStream(sampleRequest.getBytes());
        InputStream requestInputStream = sampleRequestInputStream;
        Request request = new Request();

        assertEquals(request.getClass(), clientThread.buildRequestFromInputStream(requestInputStream).getClass());
    }

    @Test
    public void testClientThreadSocketIsClosed() throws Exception {
        clientThread.closeSocket(socket);

        assertTrue(socket.isClosed());
    }
}
