package com.portatlas;

import com.portatlas.handler.Handler;
import com.portatlas.handler.GetHandler;
import com.portatlas.handler.HeadHandler;
import com.portatlas.handler.OptionsHandler;
import com.portatlas.handler.PostPutHandler;
import com.portatlas.handler.PatchHandler;
import com.portatlas.handler.DeleteHandler;
import com.portatlas.handler.MethodNotAllowedHandler;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;

import java.io.IOException;

public class Controller {

    public static Handler routeToHandler(Request request, Directory directory) throws IOException {
        Handler requestHandler = new MethodNotAllowedHandler();
        switch (request.getMethod()) {
            case RequestMethod.GET:
                return new GetHandler(request, directory);
            case RequestMethod.HEAD:
                return new HeadHandler(request);
            case RequestMethod.OPTIONS:
                return new OptionsHandler(request);
            case RequestMethod.POST:
                return new PostPutHandler(request, directory);
            case RequestMethod.PUT:
                return new PostPutHandler(request, directory);
            case RequestMethod.PATCH:
                return new PatchHandler(request, directory);
            case RequestMethod.DELETE:
                return new DeleteHandler(request, directory);
        }
        return requestHandler;
    }

    public static Response processRequest(Request request, Directory directory) throws IOException {
        return routeToHandler(request, directory).run();
    }
}
