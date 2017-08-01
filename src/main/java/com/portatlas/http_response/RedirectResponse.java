package com.portatlas.http_response;

import com.portatlas.constants.HeaderName;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class RedirectResponse implements HttpResponse {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.FOUND)
                                    .header(HeaderName.LOCATION, "/")
                                    .build();
        return response;
    }
}
