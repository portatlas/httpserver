package com.portatlas;

import com.portatlas.helpers.parser.ArgParser;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static Directory directory;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = configureServer(args);
        try {
            while(true) {
                createClientThread(serverSocket).start();
            }
        } finally {
            closeServerSocket(serverSocket);
        }
    }

    protected static ClientThread createClientThread(ServerSocket serverSocket) throws IOException {
        return new ClientThread(serverSocket.accept(), directory);
    }

    protected static ServerSocket configureServer(String[] args) throws IOException {
        directory = new Directory(ArgParser.getDirectoryPath(args));
        return new ServerSocket(Integer.parseInt(ArgParser.getPort(args)));
    }

    protected static void closeServerSocket(ServerSocket serverSocket) throws IOException {
        serverSocket.close();
    }
}
