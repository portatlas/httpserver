package com.portatlas.helpers.http_helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParameterDecoder {
    public static String decode(String queryString) throws UnsupportedEncodingException {
        return URLDecoder.decode(formatParams(queryString), "UTF-8");
    }

    public static String formatParams(String queryString) {
        return queryString.replace("=", " = ").replace("&", "\n");
    }
}
