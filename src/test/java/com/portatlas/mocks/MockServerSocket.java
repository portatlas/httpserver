package com.portatlas.mocks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {
    public MockServerSocket() throws IOException {}

    public Socket accept() throws IOException {
        return new MockSocket();
    }
}
