package com.portatlas.handler;

import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class MethodNotAllowedHandler implements Handler {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_ALLOWED)
                                    .build();
        return response;
    }
}
