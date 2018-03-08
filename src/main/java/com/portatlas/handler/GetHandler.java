package com.portatlas.handler;

import com.portatlas.cobspec.Cobspec;
import com.portatlas.Directory;
import com.portatlas.helpers.http_helpers.ContentRange;
import com.portatlas.helpers.readers.ResourceReader;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class GetHandler implements Handler {
    private Request request;
    private Directory directory;

    public GetHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run() {
        String filePath = directory.getPathName() + request.getRequestTarget();
        String statusCode = StatusCodes.NOT_FOUND;
        byte[] body = new byte[0];

        if (request.isRootRequest()) {
            return new DirectoryHandler(directory).run();
        }

        if (directory.hasFile(request.getResource())) {
            if(request.isRangeRequest()) {
                int lengthOfContent = ResourceReader.getContentLength(filePath);
                String rangeRequested = ContentRange.getRangeRequested(request);
                body = ContentRange.buildPartialContent(filePath, rangeRequested, lengthOfContent);
                statusCode = StatusCodes.PARTIAL_CONTENT;
            } else {
                try {
                    body = ResourceReader.getContent(filePath);
                    statusCode = StatusCodes.OK;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (Cobspec.requestTargets.contains(request.getRequestTarget())) {
            return Cobspec.processGetRequest(request);
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .header(HeaderName.CONTENT_TYPE, ResourceReader.getMediaType(request.getResource()))
                                    .body(body)
                                    .build();
        return response;
    }
}
