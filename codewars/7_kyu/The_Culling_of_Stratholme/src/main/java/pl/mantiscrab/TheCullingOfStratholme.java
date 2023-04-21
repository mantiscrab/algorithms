package pl.mantiscrab;

public class TheCullingOfStratholme {
    public static String purify(String s) {
        return s.replaceAll("[^ ]?[iI]+[^ ]?", "").replaceAll(" +", " ").trim();
    }
}
