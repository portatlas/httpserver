package com.portatlas.http_response;

import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class MethodNotAllowedResponse implements HttpResponse {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_ALLOWED)
                                    .build();
        return response;
    }
}
