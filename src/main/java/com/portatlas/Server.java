package com.portatlas;

import com.portatlas.helpers.ArgParser;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ArgParser argParser = new ArgParser();
    private static Directory directory = new Directory(argParser.getDirectoryPath());
    private static Router router = new Router(directory);

    public static void main(String[] args) throws IOException {
        router.addStaticRoutes();
        ServerSocket serverSocket = configureServer(args);
        try {
            while(true) {
                createClientThread(serverSocket).start();
            }
        } finally {
            closeServerSocket(serverSocket);
        }
    }

    public static ClientThread createClientThread(ServerSocket serverSocket) throws IOException {
        return new ClientThread(serverSocket.accept(), router, directory);
    }

    public static ServerSocket configureServer(String[] args) throws IOException {
        argParser.parseArgs(args);
        argParser.printArgs(argParser.getPort(), argParser.getDirectoryPath());
        return new ServerSocket(argParser.getPort());
    }

    public static void closeServerSocket(ServerSocket serverSocket) throws IOException {
        serverSocket.close();
    }
}
