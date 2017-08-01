package com.portatlas.http_response;

import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class TeapotResponse implements HttpResponse {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.TEA_POT)
                                    .body("I'm a teapot".getBytes())
                                    .build();
        return response;
    }
}
