package com.portatlas.helpers;

import java.util.Formatter;

public class Converter {
    public static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    public static String bytesToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String convertedHex = formatter.toString();
        formatter.close();
        return convertedHex;
    }
}
