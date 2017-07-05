package com.portatlas.response;

public class RedirectResponse {

    public static Response run (String redirectLocation) {
        Response response = Response.builder()
                                    .statusCode(StatusCodes.FOUND)
                                    .header("Location", redirectLocation)
                                    .build();

        return response;
    }

}
