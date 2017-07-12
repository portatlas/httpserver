package com.portatlas.response;

import com.portatlas.FileReader;

public class FileContentResponse {
    public static FileReader fileReader = new FileReader();

    public static Response run (String dir, String resource) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header("Content-Type", fileReader.getMediaType(resource))
                                    .body(fileReader.getContent(dir + resource).getBytes())
                                    .build();
        return response;
    }
}
