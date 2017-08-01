package com.portatlas.http_response;

import com.portatlas.Directory;
import com.portatlas.helpers.ResourceWriter;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class FormResponse implements HttpResponse {
    private Request request;
    private Directory directory;

    public FormResponse(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run () {
        String filePath = directory.getPathName();
        String requestTarget = request.getRequestTarget();
        String content = request.getRequestBody();
        ResourceWriter.write(filePath + requestTarget, content);

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .build();
        return response;
    }
}
