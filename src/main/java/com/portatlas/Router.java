package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.response.Response;

import java.util.HashMap;

public class Router {

    public HashMap<Request, Response> routes;

    public Router() {
        routes = new HashMap<Request, Response>();
    }

    public Response route(Request request) {
        return routes.get(request);
    }

    public boolean hasRoute(Request request) {
        return routes.containsKey(request);
    }

    public void addRoute(Request request, Response response) {
        routes.put(request, response);
    }
    
}
