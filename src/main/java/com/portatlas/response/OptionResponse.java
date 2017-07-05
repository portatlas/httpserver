package com.portatlas.response;

public class OptionResponse extends OkResponse {

    public static Response run (String fieldName, String fieldValue) {
        Response response = Response.builder()
                                    .header(fieldName, fieldValue)
                                    .build();

        return response;
    }

}
