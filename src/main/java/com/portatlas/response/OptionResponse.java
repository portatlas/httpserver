package com.portatlas.response;

import java.util.HashMap;

public class OptionResponse {

    public static Response run () {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();

        return response;
    }

    public static Response run (String fieldName, String fieldValue) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(fieldName, fieldValue)
                                    .build();

        return response;
    }

}
