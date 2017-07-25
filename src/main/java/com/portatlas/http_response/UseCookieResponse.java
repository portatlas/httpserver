package com.portatlas.http_response;

import com.portatlas.Cookie;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class UseCookieResponse implements HttpResponse {
    private Cookie cookie;

    public UseCookieResponse(Cookie cookie) {
        this.cookie = cookie;
    }

    public Response run() {

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(("mmmm " + cookie.get("type")).getBytes())
                                    .build();
        return response;
    }
}
