package com.portatlas.helpers;

import java.util.ArrayList;

public class HtmlWriter {
    public static String setLink(ArrayList<String> files) {
        String htmlFileLinks = "";
        for (String file : files) {
            htmlFileLinks += "<a href=\"/" + file + "\">" + file + "</a>\n";
        }
        return htmlFileLinks;
    }
}
