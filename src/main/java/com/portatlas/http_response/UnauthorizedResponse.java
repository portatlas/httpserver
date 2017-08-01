package com.portatlas.http_response;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class UnauthorizedResponse implements HttpResponse {
    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.UNAUTHORIZED)
                                    .header(HeaderName.WWW_AUTH, "Basic")
                                    .body("Request requires authentication".getBytes())
                                    .build();
        return response;
    }
}
