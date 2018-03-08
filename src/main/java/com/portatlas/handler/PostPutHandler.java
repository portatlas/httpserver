package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.cobspec.Cobspec;
import com.portatlas.helpers.writers.ResourceWriter;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class PostPutHandler implements Handler {
    private Request request;
    private Directory directory;

    public PostPutHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run () {
        String statusCode;

        if (directory.hasFile(request.getResource())) {
            statusCode = StatusCodes.NOT_ALLOWED;
        } else {
            String filePath = directory.getPathName() + request.getRequestTarget();
            String content = request.getRequestBody();
            ResourceWriter.write(filePath, content, directory);
            statusCode = StatusCodes.OK;
        }

        if (Cobspec.requestTargets.contains(request.getRequestTarget())) {
            return Cobspec.processPostPutRequest(request);
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .build();
        return response;
    }
}
