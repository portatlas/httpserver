package com.portatlas;

import com.portatlas.constants.HeaderName;
import com.portatlas.helpers.Authentication;
import com.portatlas.http_response.HttpResponse;
import com.portatlas.http_response.MethodNotAllowedResponse;
import com.portatlas.http_response.NotFoundResponse;
import com.portatlas.http_response.LogResponse;
import com.portatlas.http_response.UnauthorizedResponse;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.ResponseSerializer;

import java.io.IOException;

public class Controller {
    private static HttpResponse httpResponse;

    public static byte[] handleRequest(Request request, Router router, Directory directory) throws IOException {
        router.addDynamicRoutes(directory, request);
        if (isAuthorizedRequest(request)){
            verifyCredentials(request);
        } else if (router.hasRoute(request)) {
            httpResponse = router.route(request);
        } else if (isUnsupportedMethodRequest(request, directory)) {
            httpResponse = new MethodNotAllowedResponse();
        } else {
            httpResponse = new NotFoundResponse();
        }
        Response response = httpResponse.run();
        byte[] responseBytes = ResponseSerializer.serialize(response);
        return responseBytes;
    }

    private static boolean isAuthorizedRequest(Request request) {
        return request.getHeaders().containsKey(HeaderName.AUTH) && request.getResource().equals("logs");
    }

    private static void verifyCredentials(Request request) {
        String credentials = request.getHeaders().get(HeaderName.AUTH);
        try {
            httpResponse = Authentication.isValidCredentials(credentials) ? new LogResponse(request) : new UnauthorizedResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isUnsupportedMethodRequest(Request request, Directory directory) {
        return directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET);
    }
}
