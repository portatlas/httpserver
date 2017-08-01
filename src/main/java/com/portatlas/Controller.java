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

    public static byte[] handleRequest(Request request, Router router, Directory directory) throws IOException {
        if (isRangeRequest(request)) {
            httpResponse = new PartialContentResponse(request, directory.getPathName(), request.getRequestTarget());
        } else if (isAuthorizedRequest(request)) {
            verifyCredentials(request);
        } else if (router.hasRoute(request)) {
            processStaticRequest(request, router, directory);
        } else if (isUnsupportedMethodRequest(request, directory)) {
            httpResponse = new MethodNotAllowedResponse();
        } else {
            httpResponse = new NotFoundResponse();
        }
        Response response = httpResponse.run();
        byte[] responseBytes = ResponseSerializer.serialize(response);
        return responseBytes;
    }

    public static void processStaticRequest(Request request, Router router, Directory directory) {
        if(request.getMethod().equals(RequestMethod.POST) || request.getMethod().equals(RequestMethod.PUT)){
            httpResponse = new FormResponse(request, directory);
        } else {
            httpResponse = router.route(request);
        }
    }

    public static void verifyCredentials(Request request) {
        String credentials = request.getHeaders().get(HeaderName.AUTH);
        try {
            if(Authentication.isValidCredentials(credentials)) {
                httpResponse = new LogResponse(request);
            } else {
                httpResponse = new UnauthorizedResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isAuthorizedRequest(Request request) {
        return request.getHeaders().containsKey(HeaderName.AUTH) && request.getResource().equals("logs");
    }

    private static boolean isRangeRequest(Request request) {
        return request.getMethod().equals(RequestMethod.GET) && request.getHeaders().containsKey(HeaderName.RANGE);
    }

    private static boolean isUnsupportedMethodRequest(Request request, Directory directory) {
        return directory.hasFile(request.getResource()) && !request.getMethod().equals(RequestMethod.GET);
    }
}
