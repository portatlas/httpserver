package com.portatlas.http_response;

import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class OkResponse implements HttpResponse {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        return response;
    }
}
