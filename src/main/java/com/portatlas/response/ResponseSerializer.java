package com.portatlas.response;

import java.util.Map;

public class ResponseSerializer {

    public static final String CRLF = "\r\n";

    public String serialize(Response response) {
        return serializeRequestLine(response) + serializeHeaders(response) + serializeBody(response);
    }

    public String serializeRequestLine(Response response) {
        return "HTTP/1.1 " + response.getStatus() + CRLF;
    }

    private String serializeHeaders(Response response) {
        StringBuilder headersString = new StringBuilder();
        for (Map.Entry<String, String> header: response.getHeaders().entrySet()) {
            headersString.append(header.getKey() + ": " + header.getValue() + CRLF);
        }
        return headersString.toString();
    }

    private String serializeBody(Response response) {
        StringBuilder body = new StringBuilder();
        if (response.getBody().length() > 0) {
            body.append(CRLF)
                .append(response.getBody());
        }
        return body.toString();
    }

}
