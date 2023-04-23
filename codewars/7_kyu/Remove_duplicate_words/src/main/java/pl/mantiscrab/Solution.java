package pl.mantiscrab;

import java.util.Arrays;
import java.util.LinkedHashSet;

class Solution {
    public static String removeDuplicateWords(String s) {
        return new LinkedHashSet<>(Arrays.asList(s.split(" "))).stream().reduce((s1, s2) -> s1 + " " + s2).orElseThrow();
    }
}