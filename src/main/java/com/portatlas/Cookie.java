package com.portatlas;

import java.util.HashMap;

public class Cookie {
    private HashMap<String, String> jar;

    public Cookie() {
        this.jar = new HashMap<String, String>();
    }

    public boolean isEmpty() {
        return jar.isEmpty();
    }

    public void addCookie(String key, String value) {
        jar.put(key, value);
    }

    public String get(String key) {
        return jar.get(key);
    }
}
