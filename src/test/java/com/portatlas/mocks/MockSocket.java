package com.portatlas.mocks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket{
    private String sampleRequest = "GET /foo HTTP/1.1\r\nHost: en.wikipedia.org:8080\nAccept-Language: en-us,en:q=0.5\n";

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
