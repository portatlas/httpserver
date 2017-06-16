package com.portatlas.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private String statusCode = StatusCodes.OK;
    private HashMap<String, String> headers = new HashMap<String, String>();
    private String body = "";

    public ResponseBuilder statusCode(String statusCode) {
        this.statusCode = statusCode;

        return this;
    }

    public ResponseBuilder header(String fieldName, String fieldValue) {
        this.headers.put(fieldName, fieldValue);

        return this;
    }

    public ResponseBuilder body(String body) {
        this.body = body;

        return this;
    }

    public Response build() {
        Response response = new Response();
        response.setStatus(statusCode);

        for (Map.Entry<String, String> header: headers.entrySet()){
            response.setHeader(header.getKey(), header.getValue());
        }

        response.setBody(body);

        return response;
    }

}
