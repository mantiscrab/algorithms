package pl.mantiscrab;

import java.util.*;

class ParseMolecule {
    private final List<Character> formulaList;
    private Integer index = 0;
    private final Stack<Character> openedBrackets = new Stack<>();

    public static Map<String, Integer> getAtoms(String formula) {
        return new ParseMolecule(formula).parse();
    }

    private Map<String, Integer> parse() {
        Map<String, Integer> atoms = parseMolecule();
        if (!openedBrackets.isEmpty())
            throw new IllegalArgumentException();
        return atoms;
    }

    private ParseMolecule(String formula) {
        this.formulaList = formula.chars().mapToObj(c -> (char) c).toList();
    }

    private Map<String, Integer> parseMolecule() {
        AtomsAmount atoms = new AtomsAmount();
        while (areCharactersLeft()) {
            if (bracketIsOpening()) {
                openBracket();
                Map<String, Integer> map = parseMolecule();
                atoms.addAtomsAmount(map);
            } else if (bracketIsClosing()) {
                closeBracket();
                int numberOfAtoms = getNumberOfAtoms();
                atoms.multiply(numberOfAtoms);
                break;
            } else {
                Map<String, Integer> parsedAtoms = parseNext();
                atoms.addAtomsAmount(parsedAtoms);
            }
        }
        return atoms.getAtomsAmount();
    }

    private boolean areCharactersLeft() {
        return index < formulaList.size();
    }

    private boolean bracketIsOpening() {
        Character character = formulaList.get(index);
        return character.equals('(') || character.equals('{') || character.equals('[');
    }

    private void openBracket() {
        openedBrackets.push(formulaList.get(index));
        index++;
    }

    private boolean bracketIsClosing() {
        Character character = formulaList.get(index);
        return character.equals(')') || character.equals('}') || character.equals(']');
    }

    private void closeBracket() {
        if (openedBrackets.isEmpty())
            throw new IllegalArgumentException();
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
        if (!areCharactersLeft())
            throw new IllegalArgumentException();
        String atomName = getAtomName();
        int atomNumber = getNumberOfAtoms();
        return atomName.equals("") ? Map.of() : Map.of(atomName, atomNumber);
    }

    private String getAtomName() {
        StringBuilder atomNameBuilder = new StringBuilder();
        final Character ch1 = formulaList.get(index);
        if (Character.isUpperCase(ch1)) {
            atomNameBuilder.append(ch1);
            index++;
        } else
            throw new IllegalArgumentException();
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