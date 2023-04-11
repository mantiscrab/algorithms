package pl.mantiscrab;

import java.util.*;

class ParseMolecule {
    private final List<Character> formulaList;
    private Integer index = 0;
    private final Stack<Character> openedBrackets = new Stack<>();

    public static Map<String, Integer> getAtoms(String formula) {
        return new ParseMolecule(formula).parseMolecule();
    }

    private ParseMolecule(String formula) {
        this.formulaList = stringToCharacterList(formula);
    }

    private List<Character> stringToCharacterList(String formula) {
        return formula.chars().mapToObj(c -> (char) c).toList();
    }

    private Map<String, Integer> parseMolecule() {
        AtomsAmount atoms = new AtomsAmount();
        while (areCharactersLeft()) {
            if (bracketIsOpening()) {
                openBracket();
                Map<String, Integer> map = parseMolecule();
                atoms.addAtomsAmount(map);
            }
            if (bracketIsClosing()) {
                closeBracket();
                int numberOfAtoms = getNumberOfAtoms();
                atoms.multiply(numberOfAtoms);
                break;
            }
            Map<String, Integer> parsedAtoms = parseNext();
            atoms.addAtomsAmount(parsedAtoms);
        }
        return atoms.getAtomsAmount();
    }

    private boolean areCharactersLeft() {
        return index < formulaList.size();
    }

    private boolean bracketIsOpening() {
        if (areCharactersLeft()) {
            Character character = formulaList.get(index);
            return characterIsOpeningBracket(character);
        }
        return false;
    }

    private boolean characterIsOpeningBracket(Character character) {
        return character.equals('(') || character.equals('{') || character.equals('[');
    }

    private void openBracket() {
        openedBrackets.push(formulaList.get(index));
        index++;
    }

    private boolean bracketIsClosing() {
        if (areCharactersLeft()) {
            Character character = formulaList.get(index);
            return characterIsClosingBracket(character);
        }
        return false;
    }

    private static boolean characterIsClosingBracket(Character character) {
        return character.equals(')') || character.equals('}') || character.equals(']');
    }

    private void closeBracket() {
        Character closingBracket = formulaList.get(index);
        Character openingBracket = openedBrackets.pop();
        if (!openingBracketMatchesClosing(openingBracket, closingBracket))
            throw new IllegalArgumentException();
        index++;
    }

    private boolean openingBracketMatchesClosing(Character openingBracket, Character closingBracket) {
        return openingBracket.equals('(') && closingBracket.equals(')')
                || openingBracket.equals('[') && closingBracket.equals(']')
                || openingBracket.equals('{') && closingBracket.equals('}');
    }

    private Map<String, Integer> parseNext() {
        String atomName = getAtomName();
        int atomNumber = getNumberOfAtoms();
        return atomName.equals("") ? Map.of() : Map.of(atomName, atomNumber);
    }

    private String getAtomName() {
        StringBuilder atomNameBuilder = new StringBuilder();
        if (areCharactersLeft()) {
            final Character ch1 = formulaList.get(index);
            if (Character.isUpperCase(ch1)) {
                atomNameBuilder.append(ch1);
                index++;
            } else
                throw new IllegalArgumentException();
        }
        if (areCharactersLeft()) {
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
        while (areCharactersLeft()
                && Character.isDigit(formulaList.get(index))) {
            atomNumberBuilder.append(formulaList.get(index));
            index++;
        }
        atomNumber = atomNumberBuilder.toString().equals("") ? 1 : Integer.parseInt(atomNumberBuilder.toString());
        return atomNumber;
    }
}

class AtomsAmount {
    Map<String, Integer> atomsAmount = new HashMap<>();

    void addAtomAmount(String atom, Integer amount) {
        Integer atomAmount = atomsAmount.getOrDefault(atom, 0);
        atomsAmount.put(atom, amount + atomAmount);
    }

    Map<String, Integer> getAtomsAmount() {
        return Collections.unmodifiableMap(atomsAmount);
    }

    void addAtomsAmount(Map<String, Integer> parsedAtoms) {
        for (String key : parsedAtoms.keySet()) {
            addAtomAmount(key, parsedAtoms.get(key));
        }
    }

    void multiply(int number) {
        for (String key : this.atomsAmount.keySet()) {
            Integer integer = this.atomsAmount.get(key);
            this.atomsAmount.put(key, integer * number);
        }
    }
}