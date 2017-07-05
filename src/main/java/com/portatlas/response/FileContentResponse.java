package com.portatlas.response;

import com.portatlas.FileReader;

public class FileContentResponse {

    public static FileReader fileReader = new FileReader();

    public static Response run (String resource) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header("Content-Type", fileReader.getMediaType(resource))
                                    .build();

        return response;
    }

}
