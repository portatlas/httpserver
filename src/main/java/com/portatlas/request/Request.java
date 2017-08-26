package com.portatlas.request;

import com.portatlas.http_constants.HeaderName;

import java.util.HashMap;

public class Request {
    private String method;
    private String requestTarget;
    private String requestParams;
    private String httpVersion;
    private HashMap<String, String> headers;
    private String body;

    public Request() {
        headers = new HashMap<String, String>();
    }

    public Request(String method, String requestTarget, String httpVersion) {
        setMethod(method);
        setRequestTarget(requestTarget);
        setHttpVersion(httpVersion);
        headers = new HashMap<String, String>();
    }

    public Request(String method, String requestTarget, String params, String httpVersion) {
        setMethod(method);
        setRequestTarget(requestTarget);
        setRequestParams(params);
        setHttpVersion(httpVersion);
        headers = new HashMap<String, String>();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public String getResource() {
        String resource = requestTarget.replace("/", "");
        return resource;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void addHeader(String fieldName, String fieldValue ) {
        headers.put(fieldName, fieldValue);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestBody() {
        return body;
    }

    public boolean isRootRequest() {
        return getRequestTarget().equals("/");
    }

    public boolean isRangeRequest() {
        return getHeaders().containsKey(HeaderName.RANGE);
    }

    public boolean isAuthRequiredRequest() {
        return getHeaders().containsKey(HeaderName.AUTH);
    }
}
