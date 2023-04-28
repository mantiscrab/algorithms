package pl.mantiscrab;

import java.util.regex.Pattern;

class Solution {

    static String toCamelCase(String s) {
        return Pattern.compile("[_-](\\w)").matcher(s).replaceAll(r -> r.group(1).toUpperCase());
    }
}