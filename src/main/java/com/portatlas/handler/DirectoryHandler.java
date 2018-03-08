package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.helpers.writers.HtmlWriter;
import com.portatlas.http_constants.StatusCodes;
import com.portatlas.response.Response;

public class DirectoryHandler implements Handler {
    private Directory directory;

    public DirectoryHandler(Directory directory) {
        this.directory = directory;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body(HtmlWriter.setLink(directory.files).getBytes())
                                    .build();
        return response;
    }
}
