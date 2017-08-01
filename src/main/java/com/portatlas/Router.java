package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.http_response.HttpResponse;

import java.util.HashMap;

public class Router {
    public HashMap<Request, HttpResponse> routes;

    public Router() {
        routes = new HashMap<Request, HttpResponse>();
    }

    public HttpResponse route(Request request) {
        return routes.get(request);
    }

    public boolean hasRoute(Request request) {
        return routes.containsKey(request);
    }

    public void addRoute(Request request, HttpResponse httpResponse) {
        routes.put(request, httpResponse);
    }
}
