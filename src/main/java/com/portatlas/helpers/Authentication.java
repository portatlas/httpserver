package com.portatlas.helpers;

import java.util.Base64;

public class Authentication {
    private static String authType = "Basic ";

    public static String getCredentials(String authorizationValue) throws Exception {
        String credentials = "";
        if(isValidFormat(authorizationValue)){
            credentials = authorizationValue.substring(6);
        } else {
            throw new Exception("Invalid Auth Format");
        }
        return credentials;
    }

    public static String decodeCredentials(String authorizationValue) {
        return new String(Base64.getDecoder().decode(authorizationValue));
    }

    public static boolean isValidCredentials(String authorizationValue) throws Exception {
        String credentials = getCredentials(authorizationValue);
        return decodeCredentials(credentials).equals("admin:hunter2");
    }

    public static boolean isValidFormat(String authorizationValue) {
        if (validAuthType(authorizationValue) && validLength(authorizationValue)) {
            return true;
        }
        return false;
    }

    private static boolean validAuthType(String authorizationValue) {
        return getAuthType(authorizationValue).equals(authType);
    }

    private static boolean validLength(String authorizationValue) {
        return authorizationValue.length() == 26;
    }

    private static String getAuthType(String authorizationValue) {
        return authorizationValue.substring(0, 6);
    }
}
