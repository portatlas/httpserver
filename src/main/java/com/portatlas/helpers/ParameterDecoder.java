package com.portatlas.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParameterDecoder {
    public static String decode(String queryString) throws UnsupportedEncodingException {
        String params = spliceForParams(queryString);
        return URLDecoder.decode(params, "UTF-8");
    }

    public static String spliceForParams(String queryString) {
        int startOfParams = queryString.indexOf("?");
        String params = queryString.substring(startOfParams + 1).replace("=", " = ");
        return params;
    }
}
