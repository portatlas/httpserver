package com.portatlas.cobspec;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.handler.Handler;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class RedirectResponse implements Handler {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.FOUND)
                                    .header(HeaderName.LOCATION, "/")
                                    .build();
        return response;
    }
}
