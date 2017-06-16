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
    
    public static Router router = new Router();
    public static ArgParser argParser = new ArgParser();
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
        router.addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), OkResponse.run());
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

                RequestParser parser = new RequestParser();
                Request request = parser.parseRequest(requestInputStream);

                String httpResponse = handleRequest(request);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(httpResponse);
            }
        }
    }

    public static String handleRequest(Request request) {
        ResponseSerializer serializer = new ResponseSerializer();

        if (router.hasRoute(request)){
            response = router.route(request);
        } else {
            response = NotFoundResponse.run();
        }

        String responseString = serializer.serialize(response);
        System.out.println(responseString);

        return responseString;
    }

}
