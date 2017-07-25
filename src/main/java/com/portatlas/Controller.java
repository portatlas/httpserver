package com.portatlas;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.helpers.Authentication;
import com.portatlas.http_response.*;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.ResponseSerializer;

import java.io.IOException;

public class Controller {
    private static HttpResponse httpResponse;
    private static Cookie cookie = new Cookie();

    public static byte[] handleRequest(Request request, Router router, Directory directory) throws IOException {
        router.addDynamicRoutes(directory, request, cookie);
        if (isAuthorizedRequest(request)){
            verifyCredentials(request);
        } else if (router.hasRoute(request)) {
            httpResponse = router.route(request);
        } else if (isRequestWithParameters(request)) {
            processParameterRequest(request);
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
        httpResponse = Authentication.isValid(credentials) ? new LogResponse(request) : new UnauthorizedResponse();
    }

    public static void processParameterRequest(Request request) {
        if (request.getRequestTarget().contains("/parameters?")) {
            httpResponse = new ParameterResponse(request);
        } else if (request.getRequestTarget().contains("/cookie?")) {
            httpResponse = new SetCookieResponse(request, cookie);
        }
    }

    private static boolean isUnsupportedMethodRequest(Request request, Directory directory) {
        return directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET);
    }

    private static boolean isRequestWithParameters(Request request) {
        return request.getResource().contains("?");
    }
}
