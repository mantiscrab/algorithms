package pl.mantiscrab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    static String toCamelCase(String s) {
        final List<String> words = Arrays.asList(s.split("[_-](\\w)"));
        final ArrayList<String> result = new ArrayList<>();
        result.add(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            final String word = words.get(i);
            result.add(word.substring(0, 1).toUpperCase() + word.substring(1));
        }
        return String.join("", result);
    }
}