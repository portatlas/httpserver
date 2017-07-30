package com.portatlas.http_response;

import com.portatlas.constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.request.RequestParser;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class LogResponse implements HttpResponse {
    private Request request;

    public LogResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.WWW_AUTH, "Basic")
                                    .body(RequestParser.logWriter.getLogs().getBytes())
                                    .build();
        return response;
    }
}
