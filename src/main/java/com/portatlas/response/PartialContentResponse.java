package com.portatlas.response;

import com.portatlas.FileReader;
import com.portatlas.helpers.ContentRange;
import com.portatlas.request.Request;

import java.util.Arrays;

public class PartialContentResponse {
    private static FileReader fileReader = new FileReader();

    public static Response run (String dir, String resource) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.PARTIAL_CONTENT)
                                    .body(fileReader.getContent(dir + resource))
                                    .build();
        return response;
    }

    public static Response run (Request request, String dir, String resource) {
        String filePath = dir + resource;
        String rangeRequested = getRangeRequested(request);
        int lengthOfContent = fileReader.getContentLength(filePath);

        Response response = Response.builder()
                                    .statusCode(StatusCodes.PARTIAL_CONTENT)
                                    .body(buildPartialContent(filePath, rangeRequested, lengthOfContent))
                                    .build();
        return response;
    }

    public static byte[] buildPartialContent(String filePath, String rangeRequested, int lengthOfContent) {
        byte[] fullContent = fileReader.getContent(filePath);
        ContentRange contentRange = new ContentRange();
        int[] rangeRequired = contentRange.getRange(rangeRequested, lengthOfContent);
        int start = rangeRequired[0];
        int end = rangeRequired[1];
        byte[] partialContent = Arrays.copyOfRange(fullContent, start, end );
        return partialContent;
    }

    public static String getRangeRequested(Request request) {
        return request.getHeaders().get("Range").substring(6);
    }
}
