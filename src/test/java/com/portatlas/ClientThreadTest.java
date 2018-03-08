package com.portatlas;

import com.portatlas.request.RequestMethod;
import com.portatlas.mocks.MockSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientThreadTest {
    private Socket socket;
    private Directory directory;
    private ClientThread clientThread;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        socket = new MockSocket();
        directory = new Directory();
        clientThread = new ClientThread(socket, directory);
    }

    @Test
    public void testClientThreadRuns() {
        System.setOut(new PrintStream(outContent));
        clientThread.run();

        assertEquals("HTTP/1.1 404 Not Found\n", outContent.toString());
    }

    @Test
    public void testBuildRequestFromInputStream() throws Exception {
        String sampleRequest = "GET / HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";
        ByteArrayInputStream sampleRequestInputStream = new ByteArrayInputStream(sampleRequest.getBytes());
        InputStream requestInputStream = sampleRequestInputStream;

        assertEquals(RequestMethod.GET, clientThread.buildRequestFromInputStream(requestInputStream).getMethod());
        assertEquals("/", clientThread.buildRequestFromInputStream(requestInputStream).getRequestTarget());
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
}
