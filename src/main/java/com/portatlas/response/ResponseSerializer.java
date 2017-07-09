package com.portatlas.response;

import java.util.Map;

public class ResponseSerializer {

    public String serialize(Response response) {
        return serializeRequestLine(response) + serializeHeaders(response) + "\r\n";
    }

    public String serializeRequestLine(Response response) {
        return "HTTP/1.1 " + response.getStatus() + "\r\n";
    }

    private String serializeHeaders(Response response) {
        StringBuilder headersString = new StringBuilder();

        for (Map.Entry<String, String> header: response.getHeaders().entrySet()) {
            headersString.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }

        return headersString.toString();
    }

}
