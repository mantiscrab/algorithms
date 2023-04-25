package pl.mantiscrab;

import java.util.Arrays;
import java.util.stream.IntStream;

public class AntiVirus {

    public static final String IS_NOT_SAFE = "is not safe";
    public static final String IS_SAFE = "is safe";

    private int scanIntensity = 0;

    //this method is ready for you.
    public void setScanIntensity(int level) {
        scanIntensity = level;
    }

    //write this method.
    public String scanFile(File file, VirusDB database) {
        final String safeOrNot = IntStream.range(0, scanIntensity + 1)
                .mapToObj(database::getSignatures)
                .flatMap(Arrays::stream)
                .anyMatch(signature -> file.getData().toLowerCase().contains(signature.toLowerCase()))
                ? IS_NOT_SAFE : IS_SAFE;
        return file.getName() + " " + safeOrNot;
    }
}

class File {
    private final String name;
    private final String data;

    public File(String name, String data) {
        this.name = name;
        this.data = data;
    }

    //used in output
    public String getName() {
        return this.name;
    }

    //the String that you need to scan.
    public String getData() {
        return this.data;
    }

}

class VirusDB {
    private final String[] intensity1Signatures;
    private final String[] intensity2Signatures;
    private final String[] intensity3Signatures;

    public VirusDB(String[] intensity1Signatures, String[] intensity2Signatures, String[] intensity3Signatures) {
        this.intensity1Signatures = intensity1Signatures;
        this.intensity2Signatures = intensity2Signatures;
        this.intensity3Signatures = intensity3Signatures;
    }

    public String[] getSignatures(int arrayNum) {
        switch (arrayNum) {
            case 1:
                return this.intensity1Signatures;
            case 2:
                return this.intensity2Signatures;
            case 3:
                return this.intensity3Signatures;
            default:
                return new String[0];
        }
    }

}
