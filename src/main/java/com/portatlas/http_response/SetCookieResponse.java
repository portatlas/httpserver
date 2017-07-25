package com.portatlas.http_response;

import com.portatlas.Cookie;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class SetCookieResponse implements HttpResponse {
    private Request request;
    private Cookie cookie;

    public SetCookieResponse(Request request, Cookie cookie){
        this.request = request;
        this.cookie = cookie;
    }

    public Response run() {
        String cookieParam = parseCookie(request.getRequestTarget());
        setCookie(cookieParam);

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.SET_COOKIE, cookieParam)
                                    .body("Eat".getBytes())
                                    .build();
        return response;
    }

    public void setCookie(String cookieParam) {
        String cookieKeyValue[] = cookieParam.split("=");
        cookie.addCookie(cookieKeyValue[0], cookieKeyValue[1]);
    }

    public String parseCookie(String requestTarget) {
        return requestTarget.substring(requestTarget.lastIndexOf("?") + 1);
    }
}
