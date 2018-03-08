package com.portatlas.cobspec;

import com.portatlas.handler.Handler;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class ParameterResponse implements Handler {
    private Request request;

    public ParameterResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(request.getRequestParams().getBytes())
                                    .build();
        return response;
    }
}
