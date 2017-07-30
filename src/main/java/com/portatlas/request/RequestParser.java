package com.portatlas.request;

import com.portatlas.helpers.LogWriter;
import com.portatlas.helpers.ParameterDecoder;
import com.portatlas.constants.HeaderName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RequestParser {
    private static Request request;
    public static LogWriter logWriter = new LogWriter();

    public static Request parseRequest(InputStream inputStream) throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(inputStream));
        parseRequestLine(requestReader);
        String headersLine = extractHeaders(requestReader);
        parseHeaders(request, headersLine);
        if (hasBody(request)) {
            parseBody(requestReader, request);
        }
        return request;
    }

    public static void parseRequestLine(BufferedReader requestReader) throws IOException {
        int httpMethod = 0;
        int requestURI = 1;
        int httpVersion = 2;
        String requestFirstLine = requestReader.readLine();
        logWriter.logMessage(requestFirstLine);
        String[] requestLine = splitRequestLine(requestFirstLine);
        if (hasParams(requestLine[requestURI])){
            buildRequestWithParams(requestLine[httpMethod], requestLine[requestURI], requestLine[httpVersion]);
        } else {
            request = new Request(requestLine[httpMethod], requestLine[requestURI], requestLine[httpVersion]);
        }
    }

    public static String extractHeaders(BufferedReader requestReader) throws IOException {
        String linesOfHeaders = new String();
        for(String line = requestReader.readLine(); line != null; line = requestReader.readLine()) {
            linesOfHeaders += line + "\n";
            if(line.equals("")) break;
        }
        return linesOfHeaders;
    }

    public static void parseHeaders(Request request, String headers) {
        String[] headerCollection = headers.split("\n");
        for (int i  = 0; i < headerCollection.length; i++) {
            int name = 0;
            int value = 1;
            String[] httpHeader = headerCollection[i].split(": ");
            request.addHeader(httpHeader[name], httpHeader[value]);
        }
    }

    public static void parseBody(BufferedReader requestReader, Request request) throws IOException {
        int contentLength = Integer.parseInt(request.getHeaders().get(HeaderName.CONTENT_LENGTH));
        char[] body = new char[contentLength];
        requestReader.read(body, 0, contentLength);
        String bodyString = new String(body);
        request.setBody(bodyString);
    }

    private static boolean hasParams(String string) {
        return string.contains("?");
    }

    private static void buildRequestWithParams(String method, String requestURI, String httpVersion) throws UnsupportedEncodingException {
        int paramsIndex = requestURI.indexOf("?");
        String resource = requestURI.substring(0, paramsIndex);
        String queryString = requestURI.substring(paramsIndex + 1);
        String decodedParams = ParameterDecoder.decode(queryString);
        logWriter.logMessage(decodedParams);
        request = new Request(method, resource, decodedParams, httpVersion);
    }

    private static boolean hasBody(Request request) {
        return request.getHeaders().containsKey(HeaderName.CONTENT_LENGTH);
    }

    private static String[] splitRequestLine(String requestLine) {
        String[] requestLineCollection = requestLine.split(" ");
        return requestLineCollection;
    }
}
