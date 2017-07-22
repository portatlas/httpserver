package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.request.RequestParser;
import com.portatlas.response.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ArgParser argParser = new ArgParser();
    public static Router router = new Router();
    public static Directory directory = new Directory();
    public static File folder = new File(directory.defaultDir);
    public static Response response;

    public static void main(String[] args) throws IOException {
        ServerSocket server = configureServer(args);
        startServer(server);
    }

    public static ServerSocket configureServer(String[] args) throws IOException {
        argParser.parseArgs(args);
        argParser.printArgs(argParser.port, argParser.dir);

        Router router = addRoutes();

        return new ServerSocket(argParser.port);
    }

    public static Router addRoutes() {
        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), RootResponse.run(directory.listFilesForFolder(folder)));
        router.addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER), RedirectResponse.run("/"));
        router.addRoute(new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER), FileContentResponse.run("text-file.txt"));
        router.addRoute(new Request(RequestMethod.GET, "/image.jpeg" , HttpVersion.CURRENT_VER), FileContentResponse.run("image.jpeg"));
        router.addRoute(new Request(RequestMethod.GET, "/image.png" , HttpVersion.CURRENT_VER), FileContentResponse.run("image.png"));
        router.addRoute(new Request(RequestMethod.GET, "/image.gif" , HttpVersion.CURRENT_VER), FileContentResponse.run("image.gif"));
        router.addRoute(new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,HEAD,POST,OPTIONS,PUT"));
        router.addRoute(new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER), OptionResponse.run("Allow", "GET,OPTIONS"));
        router.addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());
        router.addRoute(new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER), OkResponse.run());

        return router;
    }

    public static void startServer(ServerSocket server) throws IOException {
        while (true) {
            try (Socket socket = server.accept()) {
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
