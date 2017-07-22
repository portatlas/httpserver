package com.portatlas.response;

public class NotFoundResponse {
    public static Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.NOT_FOUND)
                                    .build();
        return response;
    }

}
