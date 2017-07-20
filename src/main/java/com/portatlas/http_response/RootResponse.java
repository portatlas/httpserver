package com.portatlas.http_response;

import com.portatlas.helpers.HtmlWriter;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

import java.util.ArrayList;

public class RootResponse implements HttpResponse {
    private ArrayList directory;

    public RootResponse(ArrayList directory) {
        this.directory = directory;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(HtmlWriter.setLink(directory).getBytes())
                                    .build();
        return response;
    }
}
