package com.portatlas.response;

import com.portatlas.helpers.HtmlWriter;

import java.util.ArrayList;

public class RootResponse {

    public static Response run(ArrayList files) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(HtmlWriter.setLink(files))
                                    .build();
        return response;
    }

}
