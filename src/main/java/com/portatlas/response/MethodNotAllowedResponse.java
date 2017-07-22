package com.portatlas.response;

public class MethodNotAllowedResponse {
    public static Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_ALLOWED)
                                    .build();
        return response;
    }
}
