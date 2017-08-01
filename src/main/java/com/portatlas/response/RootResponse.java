package com.portatlas.response;

import com.portatlas.helpers.HtmlWriter;
import com.portatlas.Directory;

import java.io.File;
import java.util.ArrayList;

public class RootResponse {
    public static Response run(ArrayList directory) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(HtmlWriter.setLink(directory).getBytes())
                                    .build();
        return response;
    }
}
