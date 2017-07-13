package com.portatlas.helpers;

public class ContentRange {
    public int[] getRange(String contentRangeRequest, int lengthOfContent) {
        String[] ranges = contentRangeRequest.split("-");
        int[] partialContentRange = new int[2];
        if (contentRangeRequest.startsWith("-")) {
            partialContentRange[0] = lengthOfContent - Integer.parseInt(ranges[1]);
            partialContentRange[1] = lengthOfContent;
        } else if (contentRangeRequest.endsWith("-")) {
            partialContentRange[0] = Integer.parseInt(ranges[0]);
            partialContentRange[1] = lengthOfContent;
        } else {
            partialContentRange[0] = Integer.parseInt(ranges[0]);
            partialContentRange[1] = Integer.parseInt(ranges[1]) + 1;
        }
        return partialContentRange;
    }
}
