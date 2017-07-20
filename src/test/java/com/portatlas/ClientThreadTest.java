package com.portatlas;

import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.portatlas.request.RequestMethod;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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

        Request request = new Request(RequestMethod.GET, "/", HttpVersion.CURRENT_VER);
        assertEquals(request, clientThread.buildRequestFromInputStream(requestInputStream));
    }

    @Test
    public void testReturnResponseToOutputStream() throws Exception {
        OutputStream outputStream = new ByteArrayOutputStream();
        clientThread.returnResponseToOutputStream("Hello".getBytes(), outputStream);

        String actual = new String(outputStream.toString());
        assertEquals("Hello", actual);
    }

    @Test
    public void testClientThreadSocketIsClosed() throws Exception {
        clientThread.closeSocket(socket);

        assertTrue(socket.isClosed());
    }
}
