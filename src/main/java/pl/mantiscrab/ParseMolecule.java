package pl.mantiscrab;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParseMolecule {

    //new method starts from current index and increment in the end
    private final List<Character> formulaList;
    private Integer index = 0;

    public static Map<String, Integer> getAtoms(String formula) {
        return new ParseMolecule(formula).parseMolecule();
    }

    private ParseMolecule(String formula) {
        this.formulaList = stringToCharacterList(formula);
    }

    private Map<String, Integer> parseMolecule() {
        NumberOfAtomsMap atoms = new NumberOfAtomsMap();
        while (formulaHasCharactersLeft()) {
            if (parenthesisIsOpening()) {
                index++;
                Map<String, Integer> map = parseMolecule();
                atoms.addAtomOccurrences(map);
            }
            if (parenthesisIsClosing()) {
                index++;
                int numberOfAtoms = getNumberOfAtoms();
                atoms.multiply(numberOfAtoms);
                break;
            }
            Map<String, Integer> parsedAtoms = parseNextAtoms();
            atoms.addAtomOccurrences(parsedAtoms);
        }
        return atoms.getNumberOfAtoms();
    }

    private Map<String, Integer> parseNextAtoms() {
        String atomName = getAtomName();
        int atomNumber = getNumberOfAtoms();
        return atomName.equals("") ? Map.of() : Map.of(atomName, atomNumber);
    }

    private String getAtomName() {
        StringBuilder atomNameBuilder = new StringBuilder();
        if (formulaHasCharactersLeft()) {
            final Character ch1 = formulaList.get(index);
            if (Character.isUpperCase(ch1)) {
                atomNameBuilder.append(ch1);
                index++;
            } else
                throw new IllegalArgumentException();
        }
        if (formulaHasCharactersLeft()) {
            final char ch2 = formulaList.get(index);
            if (Character.isLowerCase(ch2)) {
                atomNameBuilder.append(ch2);
                index++;
            }
        }
        return atomNameBuilder.toString();
    }

    private int getNumberOfAtoms() {
        StringBuilder atomNumberBuilder = new StringBuilder();
        final int atomNumber;
        while (formulaHasCharactersLeft()
                && Character.isDigit(formulaList.get(index))) {
            atomNumberBuilder.append(formulaList.get(index));
            index++;
        }
        atomNumber = atomNumberBuilder.toString().equals("") ? 1 : Integer.parseInt(atomNumberBuilder.toString());
        return atomNumber;
    }

    private boolean parenthesisIsOpening() {
        if (formulaHasCharactersLeft()) {
            Character character = formulaList.get(index);
            return characterIsOpeningParenthesis(character);
        }
        return false;
    }

    private static boolean characterIsClosingParenthesis(Character character) {
        return character.equals(')') || character.equals('}') || character.equals(']');
    }

    private boolean characterIsOpeningParenthesis(Character character) {
        return character.equals('(') || character.equals('{') || character.equals('[');
    }

    private static boolean characterIsNotClosingParenthesis(Character character) {
        return !characterIsClosingParenthesis(character);
    }

    private boolean characterIsNotOpeningParenthesis(Character character) {
        return !characterIsOpeningParenthesis(character);
    }

    private boolean parenthesisIsClosing() {
        if (formulaHasCharactersLeft()) {
            Character character = formulaList.get(index);
            return characterIsClosingParenthesis(character);
        }
        return false;
    }

    private boolean parenthesisIsNotClosing() {
        if (formulaHasCharactersLeft()) {
            Character character = formulaList.get(index);
            return characterIsNotClosingParenthesis(character);
        }
        return false;
    }

    private List<Character> stringToCharacterList(String formula) {
        return formula.chars().mapToObj(c -> (char) c).toList();
    }

    private boolean formulaHasCharactersLeft() {
        return index < formulaList.size();
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

    public void addAtomOccurrences(Map<String, Integer> parsedAtoms) {
        for (String key : parsedAtoms.keySet()) {
            addAtomOccurrences(key, parsedAtoms.get(key));
        }
    }

    public void multiply(int number) {
        for (String key : this.numberOfAtoms.keySet()) {
            Integer integer = this.numberOfAtoms.get(key);
            this.numberOfAtoms.put(key, integer * number);
        }
    }
}