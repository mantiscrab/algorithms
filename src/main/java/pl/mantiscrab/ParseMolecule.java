package pl.mantiscrab;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParseMolecule {


    public static Map<String, Integer> getAtoms(String formula) {
        NumberOfAtomsMap atoms = new NumberOfAtomsMap();
        List<Character> formulaList = stringToCharacterList(formula);

        for (int i = 0; i < formulaList.size(); i++) {
            int parseUnitFinishBound = getParseUnitFinishBound(formulaList, i);
            List<Character> parseUnit = formulaList.subList(i, parseUnitFinishBound);
            AtomSymbolAndNumber atom = parse(parseUnit);
            atoms.addAtomOccurrences(atom.getName(), atom.getNumber());
            i = parseUnitFinishBound - 1;
        }
        return atoms.getNumberOfAtoms();
    }

    private static int getParseUnitFinishBound(List<Character> formulaList, final int index) {
        int internalIndex = index;
        MyCharacter character = MyCharacter.of(formulaList.get(index));
        if (character.isAlphabetic())
            internalIndex++;
        else throw new RuntimeException();
        if (internalIndex < formulaList.size()
                && MyCharacter.of(formulaList.get(internalIndex)).isLowerCase())
            internalIndex++;
        while (internalIndex < formulaList.size()
                && MyCharacter.of(formulaList.get(internalIndex)).isDigit()) {
            internalIndex++;
        }
        return internalIndex;
    }

    private static AtomSymbolAndNumber parse(List<Character> formulaList) {
        StringBuilder atomNameBuilder = new StringBuilder();
        int i = 0;
        final char ch1 = formulaList.get(i);
        if (Character.isUpperCase(ch1)) {
            atomNameBuilder.append(ch1);
            i++;
        } else
            throw new RuntimeException();
        if (listHasIndex(formulaList, i)) {
            final char ch2 = formulaList.get(i);
            if (Character.isLowerCase(ch2)) {
                atomNameBuilder.append(formulaList.get(i));
            }

        }
        String atomName = atomNameBuilder.toString();

        StringBuilder atomNumberBuilder = new StringBuilder();
        while (i < formulaList.size()
                && MyCharacter.of(formulaList.get(i)).isDigit()) {
            atomNumberBuilder.append(formulaList.get(i));
            i++;
        }
        final int atomNumber = atomNumberBuilder.toString().equals("") ? 1 : Integer.parseInt(atomNumberBuilder.toString());
        return new AtomSymbolAndNumber(atomName, atomNumber);
    }

    private static boolean listHasIndex(List<Character> formulaList, int internalIndex) {
        return internalIndex < formulaList.size();
    }


    private static List<Character> stringToCharacterList(String formula) {
        List<Character> formulaList = formula.chars().mapToObj(c -> (char) c).toList();
        return formulaList;
    }
}

class NumberOfAtomsMap {
    Map<String, Integer> numberOfAtoms = new HashMap<>();

    void addAtomOccurrences(String atom, Integer occurrencesNumber) {
        Integer atomOccurrences = numberOfAtoms.getOrDefault(atom, 0);
        numberOfAtoms.put(atom, atomOccurrences + occurrencesNumber);
    }

    void addAtomOccurrences(String atom) {
        Integer atomOccurrences = numberOfAtoms.getOrDefault(atom, 0);
        numberOfAtoms.put(atom, atomOccurrences + 1);
    }

    Map<String, Integer> getNumberOfAtoms() {
        return Collections.unmodifiableMap(numberOfAtoms);
    }

}

class MyCharacter {
    private final Character character;


    static MyCharacter of(Character character) {
        return new MyCharacter(character);
    }

    MyCharacter(Character character) {
        this.character = character;
    }

    boolean isAlphabetic() {
        return Character.isAlphabetic(character);
    }

    boolean isUpperCase() {
        return Character.isUpperCase(character);
    }

    boolean isLowerCase() {
        return Character.isLowerCase(character);
    }

    @Override
    public String toString() {
        return character.toString();
    }

    public boolean isOpeningParenthesis() {
        return character.equals('(') || character.equals('[') || character.equals('{');
    }

    public boolean isDigit() {
        return Character.isDigit(character);
    }

    public Integer toDigit() {
        return Integer.valueOf(character.toString());
    }

    public boolean isUpperCaseAlphabetic() {
        return Character.isAlphabetic(character) && Character.isUpperCase(character);
    }
}

class AtomSymbolAndNumber {
    private final String name;
    private final Integer number;

    public AtomSymbolAndNumber(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }
}