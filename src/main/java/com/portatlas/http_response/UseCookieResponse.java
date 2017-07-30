package com.portatlas.http_response;

import com.portatlas.helpers.CookieParser;
import com.portatlas.constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class UseCookieResponse implements HttpResponse {
    private Request request;

    public UseCookieResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        String rawCookies = request.getHeaders().get(HeaderName.COOKIE);
        try {
            CookieParser.parseCookies(rawCookies);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(("mmmm " + CookieParser.getCookie("type")).getBytes())
                                    .build();
        return response;
    }
}
