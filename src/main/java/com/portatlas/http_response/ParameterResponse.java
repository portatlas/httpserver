package com.portatlas.http_response;

import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class ParameterResponse implements HttpResponse {
    private Request request;

    public ParameterResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        String bodyWithDecodedParams = request.getRequestParams();
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(bodyWithDecodedParams.getBytes())
                                    .build();
        return response;
    }
}
