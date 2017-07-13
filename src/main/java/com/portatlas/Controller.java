package com.portatlas;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_response.HttpResponse;
import com.portatlas.http_response.PartialContentResponse;
import com.portatlas.http_response.MethodNotAllowedResponse;
import com.portatlas.http_response.NotFoundResponse;
import com.portatlas.response.Response;
import com.portatlas.response.ResponseSerializer;

import java.io.IOException;

public class Controller {
    private static HttpResponse httpResponse;

    public static byte[] handleRequest(Request request, Router router, Directory directory) throws IOException {
        if (request.getMethod().equals(RequestMethod.GET) && request.getHeaders().containsKey(HeaderName.RANGE)) {
            httpResponse = new PartialContentResponse(request, directory.getPathName(), request.getRequestTarget());
        } else if (router.hasRoute(request)) {
            httpResponse = router.route(request);
        } else if (directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET)) {
            httpResponse = new MethodNotAllowedResponse();
        } else {
            httpResponse = new NotFoundResponse();
        }
        Response response = httpResponse.run();
        byte[] responseByte = ResponseSerializer.serialize(response);
        return responseByte;
    }
}
