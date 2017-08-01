package com.portatlas.http_response;

import com.portatlas.constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class SetCookieResponse implements HttpResponse {
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
