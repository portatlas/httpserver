package com.portatlas;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.http_response.*;
import com.portatlas.helpers.ArgParser;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

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

    public static ClientThread createClientThread(ServerSocket serverSocket) throws IOException {
        return new ClientThread(serverSocket.accept(), router, directory);
    }

    public static ServerSocket configureServer(String[] args) throws IOException {
        argParser.parseArgs(args);
        argParser.printArgs(argParser.getPort(), argParser.getDirectoryPath());
        return new ServerSocket(argParser.getPort());
    }

    public static Router addRoutes() throws IOException {
        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), new RootResponse(directory.files));
        router.addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER), new RedirectResponse());
        router.addRoute(new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "file1"));
        router.addRoute(new Request(RequestMethod.GET, "/file2" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "file2"));
        router.addRoute(new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "text-file.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/partial_content.txt" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "partial_content.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/image.jpeg" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.jpeg"));
        router.addRoute(new Request(RequestMethod.GET, "/image.png" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.png"));
        router.addRoute(new Request(RequestMethod.GET, "/image.gif" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.gif"));
        router.addRoute(new Request(RequestMethod.GET, "/tea" , HttpVersion.CURRENT_VER), new OkResponse());
        router.addRoute(new Request(RequestMethod.GET, "/coffee" , HttpVersion.CURRENT_VER), new TeapotResponse());
        router.addRoute(new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER), new OkResponse());
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER), new OptionResponse(HeaderName.ALLOW, "GET,HEAD,POST,OPTIONS,PUT"));
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER), new OptionResponse(HeaderName.ALLOW, "GET,OPTIONS"));
        router.addRoute(new Request(RequestMethod.GET, "/form" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "form"));
        router.addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER), new OkResponse());
        router.addRoute(new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER), new OkResponse());
        router.addRoute(new Request(RequestMethod.DELETE, "/form" , HttpVersion.CURRENT_VER), new DeleteResponse(directory.getPathName(),"form"));
        router.addRoute(new Request(RequestMethod.GET, "/logs" , HttpVersion.CURRENT_VER), new UnauthorizedResponse());
        return router;
    }

    public static void closeServerSocket(ServerSocket serverSocket) throws IOException {
        serverSocket.close();
    }
}
