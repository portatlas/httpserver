package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.request.RequestParser;
import com.portatlas.response.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ArgParser argParser = new ArgParser();
    public static Directory directory = new Directory(argParser.getDirectoryPath());
    public static Router router = new Router();
    public static Response response;

    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = configureServer(args);
        argParser.parseArgs(args);
        argParser.printArgs(argParser.getPort(), argParser.getDirectoryPath());

        Router router = addRoutes();
        ServerSocket serverSocket = new ServerSocket(argParser.getPort());

//        startServer(serverSocket);

        Server server = new Server(serverSocket);

        server.startServer(serverSocket);
    }

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public static Router addRoutes() {

        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), RootResponse.run(directory));
        router.addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER), RedirectResponse.run("/"));
        router.addRoute(new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "file1"));
        router.addRoute(new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "text-file.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/image.jpeg" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "image.jpeg"));
        router.addRoute(new Request(RequestMethod.GET, "/image.png" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "image.png"));
        router.addRoute(new Request(RequestMethod.GET, "/image.gif" , HttpVersion.CURRENT_VER), FileContentResponse.run(directory.getPathName(), "image.gif"));
        router.addRoute(new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,HEAD,POST,OPTIONS,PUT"));
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,OPTIONS"));
        router.addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());

        return router;
    }

    public static void startServer(ServerSocket serverSocket) throws IOException {
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                InputStream requestInputStream = socket.getInputStream();

                Request request = RequestParser.parseRequest(requestInputStream);
                String httpResponse = handleRequest(request);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Request parseRequest(InputStream requestInputStream) throws IOException {
        RequestParser parser = new RequestParser();
        return parser.parseRequest(requestInputStream);
    }

    public static String handleRequest(Request request) {
        ResponseSerializer serializer = new ResponseSerializer();

        if (router.hasRoute(request)){
            response = router.route(request);
        } else if (directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET)){
            response = MethodNotAllowedResponse.run();
        } else {
            response = NotFoundResponse.run();
        }

        String responseString = serializer.serialize(response);
        System.out.println(responseString);

        return responseString;
    }
}
