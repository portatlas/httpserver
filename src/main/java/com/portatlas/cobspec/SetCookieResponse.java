package com.portatlas.cobspec;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.handler.Handler;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class SetCookieResponse implements Handler {
    private Request request;

    public SetCookieResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        String cookieParam = request.getRequestParams();

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.SET_COOKIE, cookieParam)
                                    .body("Eat".getBytes())
                                    .build();
        return response;
    }
}
