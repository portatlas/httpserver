package com.portatlas.response;


public class ImageContentResponse {
    public static Response run (String directory, String resource) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .body("<img src=\"/public/" + resource + "\">\n")
                                    .build();

        return response;
    }

}
