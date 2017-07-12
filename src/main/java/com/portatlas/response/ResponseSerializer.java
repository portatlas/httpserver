package com.portatlas.response;

import com.portatlas.helpers.Converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseSerializer {
    public static final String CRLF = "\r\n";
    public Converter convert = new Converter();

    public byte[] serialize(Response response) throws IOException {
        byte[] requestLine = serializeRequestLine(response);
        byte[] headers = serializeHeaders(response);
        byte[] fullResponseBytes = new byte[0];

        if(response.getBody() == null){
            fullResponseBytes = buildRequestLineAndHeaderResponseBytes(requestLine, headers);
        } else {
            byte[] requestLineAndHeader = buildRequestLineAndHeaderResponseBytes(requestLine, headers);
            byte[] body = response.getBody();
            fullResponseBytes = buildFullResponseBytes(requestLineAndHeader, body);
        }

        return fullResponseBytes;
    }

    public byte[] serializeRequestLine(Response response) {
        String statusLine = "HTTP/1.1 " + response.getStatus() + CRLF;

        return statusLine.getBytes();
    }

    private byte[] serializeHeaders(Response response) {
        StringBuilder headersString = new StringBuilder();
        for (Map.Entry<String, String> header: response.getHeaders().entrySet()) {
            headersString.append(header.getKey() + ": " + header.getValue() + CRLF);
        }

        String headers = headersString.toString();

        return headers.getBytes();
    }

    private byte[] buildRequestLineAndHeaderResponseBytes(byte[] requestLine, byte[] headers) throws IOException {
        ByteArrayOutputStream responseByte = new ByteArrayOutputStream();

        responseByte.write(requestLine);
        responseByte.write(headers);

        return responseByte.toByteArray();
    }

    private byte[] buildFullResponseBytes(byte[] requestLineAndHeader, byte[] body) throws IOException {
        ByteArrayOutputStream responseByte = new ByteArrayOutputStream();

        responseByte.write(requestLineAndHeader);
        responseByte.write(CRLF.getBytes());
        responseByte.write(body);

        return responseByte.toByteArray();
    }
}
