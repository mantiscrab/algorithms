package pl.mantiscrab;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Kata {
    public static String removeConsecutiveDuplicates(String s) {
        final Set<String> words = Arrays.stream(s.split(" ")).collect(Collectors.toSet());
        for (final String word : words) {
            s = s.replaceAll("(^| )(%s( |$)){2,}".formatted(word), "$1$2");
        }
        return s.trim();
    }
}