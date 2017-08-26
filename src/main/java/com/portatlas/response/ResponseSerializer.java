package com.portatlas.response;

import com.portatlas.http_constants.HttpVersion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseSerializer {
    public static final String CRLF = "\r\n";

    public static byte[] serialize(Response response) throws IOException {
        byte[] requestLine = serializeRequestLine(response);
        byte[] headers = serializeHeaders(response);
        byte[] fullResponseBytes;
        if(response.getBody() == null) {
            fullResponseBytes = buildRequestLineAndHeaderResponseBytes(requestLine, headers);
        } else {
            byte[] requestLineAndHeader = buildRequestLineAndHeaderResponseBytes(requestLine, headers);
            byte[] body = response.getBody();
            fullResponseBytes = buildFullResponseBytes(requestLineAndHeader, body);
        }
        return fullResponseBytes;
    }

    public static byte[] serializeRequestLine(Response response) {
        String statusLine = HttpVersion.CURRENT_VER + " " + response.getStatus() + CRLF;
        return statusLine.getBytes();
    }

    private static byte[] serializeHeaders(Response response) {
        StringBuilder headersString = new StringBuilder();
        for (Map.Entry<String, String> header: response.getHeaders().entrySet()) {
            headersString.append(header.getKey() + ": " + header.getValue() + CRLF);
        }
        String headers = headersString.toString();
        return headers.getBytes();
    }

    private static byte[] buildRequestLineAndHeaderResponseBytes(byte[] requestLine, byte[] headers) throws IOException {
        ByteArrayOutputStream responseByte = new ByteArrayOutputStream();
        responseByte.write(requestLine);
        responseByte.write(headers);
        return responseByte.toByteArray();
    }

    private static byte[] buildFullResponseBytes(byte[] requestLineAndHeader, byte[] body) throws IOException {
        ByteArrayOutputStream responseByte = new ByteArrayOutputStream();
        responseByte.write(requestLineAndHeader);
        responseByte.write(CRLF.getBytes());
        responseByte.write(body);
        return responseByte.toByteArray();
    }
}
