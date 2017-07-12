package com.portatlas.response;

import com.portatlas.FileReader;
import java.io.IOException;

public class ImageContentResponse {

    public static FileReader fileReader = new FileReader();

    public static Response run (String directory, String resource) throws IOException {
        String filePath = directory + resource;

        Response response = Response.builder()
                                    .statusCode(StatusCodes.OK)
                                    .header("Content-Type", fileReader.getMediaType(resource))
                                    .body(fileReader.getImage(filePath))
                                    .build();

        return response;
    }

}
