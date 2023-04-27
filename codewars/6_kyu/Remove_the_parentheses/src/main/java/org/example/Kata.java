package org.example;

public class Kata {
    public static String removeParentheses(final String str) {
        String result = str;
        while (result.contains("(")
                && result.contains(")")
                && result.indexOf("(") < result.lastIndexOf(")")) {
            result = result.replaceAll("\\([^()]*?\\)", "");
        }
        return result;
    }
}