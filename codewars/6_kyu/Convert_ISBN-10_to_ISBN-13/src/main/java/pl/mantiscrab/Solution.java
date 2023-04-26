package pl.mantiscrab;

import java.util.Arrays;

public class Solution {
    public static String isbnConverter(String isbn) {
        final String isbn12 = isbn.replaceFirst("(.*)[\\dX]$", "978-$1");
        final int checkDigit = countCheckDigit(isbn12);
        return isbn12 + checkDigit;
    }

    private static int countCheckDigit(final String isbn12) {
        final String isbn12WithoutHyphens = isbn12.replaceAll("-", "");
        final Integer[] integers = Arrays.stream(isbn12WithoutHyphens.split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        int numberSum = 0;
        for (int i = 0; i < integers.length; i++) {
            numberSum += (i % 2 == 0) ? integers[i] : integers[i] * 3;
        }
        final int modulo = numberSum % 10;
        return modulo == 0 ? 0 : 10 - modulo;
    }
}
