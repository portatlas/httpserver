package com.portatlas.http_response;

import com.portatlas.helpers.ResourceReader;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class FileContentResponse implements HttpResponse {
    private String directoryPath;
    private String resource;

    public FileContentResponse(String directoryPath, String resource){
        this.directoryPath = directoryPath;
        this.resource = resource;
    }

    public Response run() {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.CONTENT_TYPE, ResourceReader.getMediaType(resource))
                                    .body(ResourceReader.getContent(directoryPath + resource))
                                    .build();
        return response;
    }
}
