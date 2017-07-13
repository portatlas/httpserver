package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.PartialContentResponse;
import com.portatlas.response.MethodNotAllowedResponse;
import com.portatlas.response.NotFoundResponse;
import com.portatlas.response.ResponseSerializer;

import java.io.IOException;

public class Controller {
    private static Response response;

    public static byte[] handleRequest(Request request, Router router, Directory directory) throws IOException {
        if (request.getMethod().equals(RequestMethod.GET) && request.getHeaders().containsKey("Range")) {
            response = PartialContentResponse.run(request, directory.getPathName(), request.getRequestTarget());
        } else if (router.hasRoute(request)) {
            response = router.route(request);
        } else if (directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET)) {
            response = MethodNotAllowedResponse.run();
        } else {
            response = NotFoundResponse.run();
        }
        byte[] responseByte = ResponseSerializer.serialize(response);
        return responseByte;
    }
}
