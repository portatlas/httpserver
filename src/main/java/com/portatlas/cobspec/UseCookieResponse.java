package com.portatlas.cobspec;

import com.portatlas.handler.Handler;
import com.portatlas.helpers.parser.CookieParser;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class UseCookieResponse implements Handler {
    private Request request;

    public UseCookieResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        String rawCookies = request.getHeaders().get(HeaderName.COOKIE);
        String cookie = new String();
        try {
            cookie = CookieParser.getCookieFromKey(rawCookies, "type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(("mmmm " + cookie).getBytes())
                                    .build();
        return response;
    }
}
