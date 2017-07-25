package com.portatlas.http_response;

import com.portatlas.Directory;
import com.portatlas.helpers.Converter;
import com.portatlas.helpers.ResourceReader;
import com.portatlas.helpers.ResourceWriter;
import com.portatlas.helpers.ShaEncoder;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

public class PatchResponse implements HttpResponse {
    private Request request;
    private Directory directory;

    public PatchResponse(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response run() {
        String filePath = directory.getPathName() + request.getRequestTarget();
        String resourceContent = Converter.bytesToString(ResourceReader.getContent(filePath));
        String SHA1ofContent = ShaEncoder.encode(resourceContent);
        if (SHA1ofContent.equals(request.getHeaders().get(HeaderName.IF_MATCH))) {
            ResourceWriter.write(filePath, request.getRequestBody());
        }

        Response response = Response.builder()
                                    .statusCode(StatusCodes.NO_CONTENT)
                                    .build();
        return response;
    }
}
