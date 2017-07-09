package com.portatlas.response;

public class OkResponse {

    public static Response run () {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();

        return response;
    }

}
