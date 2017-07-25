package com.portatlas;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.http_response.*;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    public HashMap<Request, HttpResponse> routes;
    private Directory directory;

    public Router(Directory directory) {
        routes = new HashMap<Request, HttpResponse>();
        this.directory = directory;
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

    public void addStaticRoutes() throws IOException {
        addRoute(new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER), new RootResponse(directory.files));
        addRoute(new Request(RequestMethod.GET, "/redirect" , HttpVersion.CURRENT_VER), new RedirectResponse());
        addRoute(new Request(RequestMethod.GET, "/file1" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "file1"));
        addRoute(new Request(RequestMethod.GET, "/file2" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "file2"));
        addRoute(new Request(RequestMethod.GET, "/text-file.txt" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "text-file.txt"));
        addRoute(new Request(RequestMethod.GET, "/partial_content.txt" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "partial_content.txt"));
        addRoute(new Request(RequestMethod.GET, "/image.jpeg" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.jpeg"));
        addRoute(new Request(RequestMethod.GET, "/image.png" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.png"));
        addRoute(new Request(RequestMethod.GET, "/image.gif" , HttpVersion.CURRENT_VER), new ImageContentResponse(directory.getPathName(), "image.gif"));
        addRoute(new Request(RequestMethod.GET, "/tea" , HttpVersion.CURRENT_VER), new OkResponse());
        addRoute(new Request(RequestMethod.GET, "/coffee" , HttpVersion.CURRENT_VER), new TeapotResponse());
        addRoute(new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER), new OkResponse());
        addRoute(new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER), new OptionResponse(HeaderName.ALLOW, "GET,HEAD,POST,OPTIONS,PUT"));
        addRoute(new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER), new OptionResponse(HeaderName.ALLOW, "GET,OPTIONS"));
        addRoute(new Request(RequestMethod.GET, "/form" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "form"));
        addRoute(new Request(RequestMethod.DELETE, "/form" , HttpVersion.CURRENT_VER), new DeleteResponse(directory.getPathName(),"form"));
        addRoute(new Request(RequestMethod.GET, "/logs" , HttpVersion.CURRENT_VER), new UnauthorizedResponse());
        addRoute(new Request(RequestMethod.GET, "/patch-content.txt" , HttpVersion.CURRENT_VER), new FileContentResponse(directory.getPathName(), "patch-content.txt"));
    }

    public void addDynamicRoutes(Directory directory, Request request, Cookie cookie) throws IOException {
        addRoute(new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER), new FormResponse(request, directory));
        addRoute(new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER), new FormResponse(request, directory));
        addRoute(new Request(RequestMethod.PATCH, "/patch-content.txt" , HttpVersion.CURRENT_VER), new PatchResponse(request, directory));
        addRoute(new Request(RequestMethod.GET, "/partial_content.txt" , HttpVersion.CURRENT_VER), new PartialContentResponse(request, directory.getPathName(), request.getRequestTarget()));
        addRoute(new Request(RequestMethod.GET, "/eat_cookie" , HttpVersion.CURRENT_VER), new UseCookieResponse(cookie));
    }
}
