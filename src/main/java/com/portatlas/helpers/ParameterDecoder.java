package com.portatlas.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParameterDecoder {
    public static String decode(String queryString) throws UnsupportedEncodingException {
        String params = formatParams(queryString);
        return URLDecoder.decode(params, "UTF-8");
    }

    public static String formatParams(String queryString) {
        String params = queryString.replace("=", " = ").replace("&", "\n");
        return params;
    }
}
