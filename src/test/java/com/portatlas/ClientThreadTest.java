package com.portatlas;

import com.portatlas.http_constants.HttpVersion;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.mocks.MockSocket;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientThreadTest {
    private String sampleRequest = "GET /foo HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
    private Socket socket;
    private Directory directory;
    private Router router;
    private ClientThread clientThread;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws IOException {
        socket = new MockSocket();
        directory = new Directory();
        router = new Router(directory);
        clientThread = new ClientThread(socket, router, directory);
    }
    @Test
    public void testClientThreadRuns() {
        System.setOut(new PrintStream(outContent));
        clientThread.run();

        assertTrue(outContent.toString().contains("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void testBuildRequestFromInputStream() throws IOException {
        String sampleRequest = "GET / HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
        ByteArrayInputStream sampleRequestInputStream = new ByteArrayInputStream(sampleRequest.getBytes());
        InputStream requestInputStream = sampleRequestInputStream;

        Request request = new Request(RequestMethod.GET, "/", HttpVersion.CURRENT_VER);
        assertEquals(request, clientThread.buildRequestFromInputStream(requestInputStream));
    }

    @Test
    public void testReturnResponseToOutputStream() throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        clientThread.returnResponseToOutputStream("Hello".getBytes(), outputStream);

        String actual = new String(outputStream.toString());
        assertEquals("Hello", actual);
    }

    @Test
    public void testClientThreadSocketIsClosed() throws IOException {
        clientThread.closeSocket(socket);

        assertTrue(socket.isClosed());
    }

    private class MockSocket extends Socket {
        public MockSocket() throws IOException {}

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(sampleRequest.getBytes());
        }

        public OutputStream getOutputStream() throws IOException {
            return new OutputStream() {
                @Override
                public void write(int b) throws IOException {}
            };
        }
    }
}
