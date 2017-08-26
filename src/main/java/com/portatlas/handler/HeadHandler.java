package com.portatlas.handler;

import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class HeadHandler implements Handler {
    private Request request;

    public HeadHandler(Request request) {
        this.request = request;
    }

    public Response run() {
        String statusCode = StatusCodes.NOT_FOUND;

        if (request.isRootRequest()) {
            statusCode = StatusCodes.OK;
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .build();
        return response;
    }
}
