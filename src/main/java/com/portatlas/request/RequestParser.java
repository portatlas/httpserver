package com.portatlas.request;

import com.portatlas.helpers.LogWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {
    public static LogWriter logWriter = new LogWriter();
    public static Request parseRequest(InputStream inputStream) throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = requestReader.readLine();
        logWriter.logMessage(requestLine);
        String[] requestLineColl = parseRequestLine(requestLine);
        Integer httpMethod = 0;
        Integer requestURI = 1;
        Integer httpVersion = 2;
        Request request = new Request(requestLineColl[httpMethod], requestLineColl[requestURI], requestLineColl[httpVersion]);

        String headersLine = extractHeaders(requestReader);
        parseHeaders(request, headersLine);

        if (hasBody(request)) {
            parseBody(requestReader, request);
        }
        return request;
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
            String[] headerNameValue = headerCollection[i].split(": ");
            request.addHeader(headerNameValue[0], headerNameValue[1]);
        }
    }

    public static boolean hasBody(Request request) {
        return request.getHeaders().containsKey("Content-Length");
    }

    public static void parseBody(BufferedReader requestReader, Request request) throws IOException {
        int contentLength = Integer.parseInt(request.getHeaders().get("Content-Length"));
        char[] body = new char[contentLength];
        requestReader.read(body, 0, contentLength);
        String bodyString = new String(body);
        request.setBody(bodyString);
    }

    private static String[] parseRequestLine(String requestLine) {
        String[] requestLineCollection = requestLine.split(" ");
        return requestLineCollection;
    }
}
