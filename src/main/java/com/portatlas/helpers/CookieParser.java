package com.portatlas.helpers;

import java.util.HashMap;

public class CookieParser {
    private static HashMap<String, String> cookies = new HashMap<String, String>();

    public static void parseCookies(String rawCookie) throws Exception {
        String[] cookieArray = rawCookie.split(";");
        for (int i = 0; i < cookieArray.length; i++) {
            String rawCookieKeyValue[] = cookieArray[i].trim().split("=");
            if (rawCookieKeyValue.length != 2) {
                throw new Exception("Invalid Cookie Format");
            } else {
                String cookieKey = rawCookieKeyValue[0].trim();
                String cookieValue = rawCookieKeyValue[1].trim();
                cookies.put(cookieKey, cookieValue);
            }
        }
    }

    public static String getCookie(String cookieKey) {
        return cookies.get(cookieKey);
    }
}
