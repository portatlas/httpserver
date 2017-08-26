package com.portatlas.cobspec;

import com.portatlas.handler.Handler;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class TeapotResponse implements Handler {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.TEA_POT)
                                    .body("I'm a teapot".getBytes())
                                    .build();
        return response;
    }
}
