package com.portatlas.helpers.http_helpers;

import com.portatlas.helpers.readers.ResourceReader;
import com.portatlas.http_constants.HeaderName;
import com.portatlas.request.Request;

import java.util.Arrays;

public class ContentRange {
    private static String dash = "-";

    public static int[] getRange(String contentRangeRequest, int lengthOfContent) {
        String[] ranges = contentRangeRequest.split(dash);
        int[] partialContentRange = new int[2];
        if (contentRangeRequest.startsWith(dash)) {
            partialContentRange[0] = lengthOfContent - Integer.parseInt(ranges[1]);
            partialContentRange[1] = lengthOfContent;
        } else if (contentRangeRequest.endsWith(dash)) {
            partialContentRange[0] = Integer.parseInt(ranges[0]);
            partialContentRange[1] = lengthOfContent;
        } else {
            partialContentRange[0] = Integer.parseInt(ranges[0]);
            partialContentRange[1] = Integer.parseInt(ranges[1]) + 1;
        }
        return partialContentRange;
    }

    public static byte[] buildPartialContent(String filePath, String rangeRequested, int lengthOfContent) {
        byte[] fullContent = new byte[0];
        try {
            fullContent = ResourceReader.getContent(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] rangeRequired = getRange(rangeRequested, lengthOfContent);
        int start = rangeRequired[0];
        int end = rangeRequired[1];
        byte[] partialContent = Arrays.copyOfRange(fullContent, start, end );
        return partialContent;
    }

    public static String getRangeRequested(Request request) {
        return request.getHeaders().get(HeaderName.RANGE).substring(6);
    }
}
