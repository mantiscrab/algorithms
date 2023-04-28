package pl.mantiscrab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    static String toCamelCase(String s) {
        final List<String> words = Arrays.asList(s.split("[_-]"));
        final ArrayList<String> strings = new ArrayList<>();
        strings.add(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            final String word = words.get(i);
            final String firstLetter = word.length() == 0 ? "" : Character.valueOf(word.charAt(0)).toString();
            final String capitalizeWord = word.replaceFirst(firstLetter, firstLetter.toUpperCase());
            strings.add(capitalizeWord);
        }
        return String.join("", strings);
    }
}