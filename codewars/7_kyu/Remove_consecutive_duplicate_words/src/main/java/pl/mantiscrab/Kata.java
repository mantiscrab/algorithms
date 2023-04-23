package pl.mantiscrab;

public class Kata {
    public static String removeConsecutiveDuplicates(String s) {
        return s.replaceAll("(\\b\\S+)( \\1)+\\b", "$1");
    }
}