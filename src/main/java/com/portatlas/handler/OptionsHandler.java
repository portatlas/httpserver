package com.portatlas.handler;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class OptionsHandler implements Handler {
    private Request request;

    public OptionsHandler(Request request) {
        this.request = request;
    }

    public Response run() {
        String fieldValue = "GET,OPTIONS";
        if(request.getRequestTarget().equals("/method_options")) {
            fieldValue = "GET,HEAD,POST,OPTIONS,PUT";
        }

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.ALLOW, fieldValue)
                                    .build();
        return response;
    }
}
