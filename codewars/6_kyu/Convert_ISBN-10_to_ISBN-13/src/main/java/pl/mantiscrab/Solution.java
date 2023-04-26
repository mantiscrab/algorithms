package pl.mantiscrab;

public class Solution {
    public static String isbnConverter(String isbn) {
        final String isbnWithPrefixAddedSuffixRemoved = isbn.replaceFirst("(.*)[\\dX]$", "978-$1");
        final int checkDigit = countCheckDigit(isbnWithPrefixAddedSuffixRemoved);
        return isbnWithPrefixAddedSuffixRemoved + checkDigit;
    }

    private static int countCheckDigit(final String isbnWithPrefixAddedAndSuffixRemoved) {
        final String numbersWithoutHyphens = isbnWithPrefixAddedAndSuffixRemoved.replaceAll("-", "");
        final String[] split = numbersWithoutHyphens.split("");
        int numberSum = 0;
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0)
                numberSum += Integer.parseInt(split[i]);
            else
                numberSum += Integer.parseInt(split[i]) * 3;
        }
        final int modulo = numberSum % 10;
        return modulo == 0 ? 0 : 10 - modulo;
    }
}
