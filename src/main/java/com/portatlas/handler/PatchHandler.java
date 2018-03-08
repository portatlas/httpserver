package com.portatlas.handler;

import com.portatlas.Directory;
import com.portatlas.helpers.Converter;
import com.portatlas.helpers.readers.ResourceReader;
import com.portatlas.helpers.writers.ResourceWriter;
import com.portatlas.helpers.http_helpers.ShaEncoder;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

public class PatchHandler implements Handler {
    private Request request;
    private Directory directory;

    public PatchHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run() {
        String statusCode;
        String resourceContent = new String();
        String filePath = directory.getPathName() + request.getRequestTarget();
        try {
            resourceContent = Converter.bytesToString(ResourceReader.getContent(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String SHA1ofContent = ShaEncoder.encode(resourceContent);

        if (SHA1ofContent.equals(request.getHeaders().get(HeaderName.IF_MATCH))) {
            statusCode = StatusCodes.NO_CONTENT;
            ResourceWriter.write(filePath, request.getRequestBody(), directory);
        } else {
            statusCode = StatusCodes.PRECONDITIONED_FAILED;
        }

        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .build();
        return response;
    }
}
