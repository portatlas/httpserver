package com.portatlas.response;

public class TeapotResponse {
    public static Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.TEA_POT)
                                    .body("I'm a teapot".getBytes())
                                    .build();
        return response;
    }
}
