package com.portatlas.handler;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.helpers.http_helpers.Authentication;
import com.portatlas.request.Request;
import com.portatlas.request.RequestParser;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class AuthHandler implements Handler {
    private Request request;
    private String body = "Request requires authentication";

    public AuthHandler(Request request) {
        this.request = request;
    }

    public Response run() {
        String statusCode = StatusCodes.UNAUTHORIZED;

        if (request.getHeaders().containsKey(HeaderName.AUTH)) {
            try {
                if(Authentication.isValidCredentials(request.getHeaders().get(HeaderName.AUTH))) {
                    body = RequestParser.logWriter.getMessage();
                    statusCode = StatusCodes.OK;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .header(HeaderName.WWW_AUTH, "Basic")
                                    .body(body.getBytes())
                                    .build();
        return response;
    }
}
