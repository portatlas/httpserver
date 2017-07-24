package com.portatlas.http_response;


import com.portatlas.helpers.ParameterDecoder;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

import java.io.UnsupportedEncodingException;

public class ParameterResponse implements HttpResponse {
    private Request request;

    public ParameterResponse(Request request) {
        this.request = request;
    }

    public Response run() {
        byte[] bodyContent = createBodyFromParams();
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(bodyContent)
                                    .build();
        return response;
    }

    private byte[] createBodyFromParams() {
        byte[] bodyContent = new byte[0];
        try {
            bodyContent = ParameterDecoder.decode(request.getRequestTarget()).getBytes();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return bodyContent;
    }

}
