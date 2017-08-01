package com.portatlas.http_response;

import com.portatlas.helpers.ResourceWriter;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class DeleteResponse implements HttpResponse {
    private String directoryPath;
    private String resource;

    public DeleteResponse(String directoryPath, String resource) {
        this.directoryPath = directoryPath;
        this.resource = resource;
    }

    public Response run() {
        ResourceWriter.delete(directoryPath + "/" + resource);

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        return response;
    }
}
