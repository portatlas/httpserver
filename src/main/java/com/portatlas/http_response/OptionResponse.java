package com.portatlas.http_response;

import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class OptionResponse implements HttpResponse {
    private String fieldName;
    private String fieldValue;

    public OptionResponse(String fieldName, String fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(fieldName, fieldValue)
                                    .build();
        return response;
    }
}
