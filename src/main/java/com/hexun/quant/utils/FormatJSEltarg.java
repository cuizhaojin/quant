package com.hexun.quant.utils;

/**
 * Created by hexun on 2017/1/12.
 */
public class FormatJSEltarg {

    public static String format(String s) {
        if (s != null && s.length() > 0) {
            s = s.replaceAll("(\r|\n|\r\n|\n\r)", " ");
            s = s.replaceAll("\"", "\\\\" + "\"");
            s = s.replaceAll("\'", "\\\\" + "\'");
            return s;
        } else {
            return "";
        }
    }
}
