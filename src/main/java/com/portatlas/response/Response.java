package com.portatlas.response;

import java.util.HashMap;

public class Response {
    private String status;
    private String httpVersion;
    private HashMap<String, String> headers;
    private byte[] body;

    Response() {
        headers = new HashMap<String, String>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HashMap<String,String> getHeaders() {
        return headers;
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public void setHeader(String fieldName, String fieldValue) {
        headers.put(fieldName, fieldValue);
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }
}
