package com.portatlas.helpers.parser;

public class CookieParser {
    private static int key = 0;
    private static int cookieValue = 1;

    public static String getCookieFromKey(String rawCookie, String targetCookieKey) throws Exception {
        if(rawCookie.contains(targetCookieKey)) {
            String[] cookieArray = rawCookie.split(";");
            for (int i = 0; i < cookieArray.length; i++) {
                String cookieTuple[] = cookieArray[i].trim().split("=");
                if (cookieTuple.length != 2) {
                    throw new Exception("Invalid Cookie Format");
                }
                if (cookieTuple[key].trim().equals(targetCookieKey)) {
                    return cookieTuple[cookieValue].trim();
                }
            }
        }
        return null;
    }
}
