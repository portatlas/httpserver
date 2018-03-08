package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.cobspec.Cobspec;
import com.portatlas.helpers.writers.ResourceWriter;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class DeleteHandler implements Handler {
    private Request request;
    private Directory directory;

    public DeleteHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run() {
        String statusCode = StatusCodes.NOT_FOUND;

        if(directory.hasFile(request.getResource())) {
            try {
                ResourceWriter.delete(directory.getPathName() + request.getRequestTarget());
                statusCode = StatusCodes.OK;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Cobspec.requestTargets.contains(request.getRequestTarget())) {
            return Cobspec.processDeleteRequest(request);
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .build();
        return response;
    }
}
