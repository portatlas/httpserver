package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.*;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static ArgParser argParser = new ArgParser();
    public static Directory directory = new Directory(argParser.getDirectoryPath());
    public static Router router = new Router();

    public static void main(String[] args) throws IOException {
        router = addRoutes();
        ServerSocket serverSocket = configureServer(args);
        try {
            while(true) {
                new ClientThread(serverSocket.accept(), router, directory).start();
            }
        } finally {
            closeServerSocket(serverSocket);
        }
    }

    public static ServerSocket configureServer(String[] args) throws IOException {
        argParser.parseArgs(args);
        argParser.printArgs(argParser.getPort(), argParser.getDirectoryPath());
        return new ServerSocket(argParser.getPort());
    }

    public static Router addRoutes() throws IOException {
        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), RootResponse.run(directory.files));
        router.addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER), RedirectResponse.run("/"));
        router.addRoute(new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "file1"));
        router.addRoute(new Request(RequestMethod.GET, "/file2" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "file2"));
        router.addRoute(new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "text-file.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/partial_content.txt" , HttpVersion.CURRENT_VER), PartialContentResponse.run(directory.getPathName(), "partial_content.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/image.jpeg" , HttpVersion.CURRENT_VER), ImageContentResponse.run(directory.getPathName(), "image.jpeg"));
        router.addRoute(new Request(RequestMethod.GET, "/image.png" , HttpVersion.CURRENT_VER), ImageContentResponse.run(directory.getPathName(), "image.png"));
        router.addRoute(new Request(RequestMethod.GET, "/image.gif" , HttpVersion.CURRENT_VER), ImageContentResponse.run(directory.getPathName(), "image.gif"));
        router.addRoute(new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,HEAD,POST,OPTIONS,PUT"));
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,OPTIONS"));
        router.addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());
        return router;
    }

    public static void closeServerSocket(ServerSocket serverSocket) throws IOException {
        serverSocket.close();
    }
}
