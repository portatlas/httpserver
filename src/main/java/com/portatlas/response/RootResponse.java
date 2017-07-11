package com.portatlas.response;


import com.portatlas.helpers.HtmlWriter;
import com.portatlas.Directory;

public class RootResponse {
    public static Response run(Directory directory) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(HtmlWriter.setLink(files))
                                    .build();
        return response;
    }
}
