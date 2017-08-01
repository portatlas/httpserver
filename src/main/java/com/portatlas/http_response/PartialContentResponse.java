package com.portatlas.http_response;

import com.portatlas.helpers.ResourceReader;
import com.portatlas.helpers.ContentRange;
import com.portatlas.constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class PartialContentResponse implements HttpResponse {
    private Request request;
    private String filePath;

    public PartialContentResponse(Request request, String directoryPath, String resource) {
        this.request = request;
        this.filePath = directoryPath + resource;
    }

    public Response run() {
        String statusCode = StatusCodes.OK;
        byte[] body;

        if(request.getHeaders().containsKey(HeaderName.RANGE)) {
            statusCode = StatusCodes.PARTIAL_CONTENT;
            int lengthOfContent = ResourceReader.getContentLength(filePath);
            String rangeRequested = ContentRange.getRangeRequested(request);
            body = ContentRange.buildPartialContent(filePath, rangeRequested, lengthOfContent);
        } else {
            body = ResourceReader.getContent(filePath);
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .body(body)
                                    .build();
        return response;
    }
}
