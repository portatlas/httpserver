package com.portatlas.http_response;

import com.portatlas.FileReader;
import com.portatlas.helpers.ContentRange;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class PartialContentResponse implements HttpResponse {
    private Request request;
    private String directoryPath;
    private String resource;

    public PartialContentResponse(Request request, String directoryPath, String resource){
        this.request = request;
        this.directoryPath = directoryPath;
        this.resource = resource;
    }

    public Response run() {
        String filePath = directoryPath + resource;
        String rangeRequested = ContentRange.getRangeRequested(request);
        int lengthOfContent = FileReader.getContentLength(filePath);

        Response response = Response.builder()
                                    .statusCode(StatusCodes.PARTIAL_CONTENT)
                                    .body(ContentRange.buildPartialContent(filePath, rangeRequested, lengthOfContent))
                                    .build();
        return response;
    }
}
