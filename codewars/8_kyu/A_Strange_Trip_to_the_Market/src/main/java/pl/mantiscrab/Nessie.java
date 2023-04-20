package pl.mantiscrab;

public class Nessie {
    public static boolean isLockNessMonster(String s){
        return s.matches(".*tree fiddy.*") || s.matches(".*three fifty.*") || s.matches(".*3.50.*");
    }
}