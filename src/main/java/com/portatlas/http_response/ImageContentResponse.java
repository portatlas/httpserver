package com.portatlas.http_response;

import com.portatlas.FileReader;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class ImageContentResponse implements HttpResponse {
    private String directoryPath;
    private String resource;

    public ImageContentResponse(String directoryPath, String resource) {
        this.directoryPath = directoryPath;
        this.resource = resource;
    }

    public Response run() {
        String filePath = directoryPath + resource;

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header(HeaderName.CONTENT_TYPE, FileReader.getMediaType(resource))
                                    .body(FileReader.getContent(filePath))
                                    .build();
        return response;
    }
}
