package pl.mantiscrab;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParseMolecule {
    private final NumberOfAtomsMap atoms = new NumberOfAtomsMap();
    private final List<Character> formulaList;
    private Integer index = 0;

    public ParseMolecule(String formula) {
        this.formulaList = stringToCharacterList(formula);
    }

    public static Map<String, Integer> getAtoms(String formula) {
        return new ParseMolecule(formula).parseMolecule();
    }

    private Map<String, Integer> parseMolecule() {
        while (formulaHasCharactersLeft()) {
            Map<String, Integer> parsedAtoms = parse();
            atoms.addAtomOccurrences(parsedAtoms);
        }
        return atoms.getNumberOfAtoms();
    }

    private Map<String, Integer> parse() {
        StringBuilder atomNameBuilder = new StringBuilder();
        final Character ch1 = formulaList.get(index);
        if (Character.isUpperCase(ch1)) {
            atomNameBuilder.append(ch1);
            index++;
            if (formulaHasCharactersLeft()) {
                final char ch2 = formulaList.get(index);
                if (Character.isLowerCase(ch2)) {
                    atomNameBuilder.append(formulaList.get(index));
                    index++;
                }
            }
            String atomName = atomNameBuilder.toString();

            StringBuilder atomNumberBuilder = new StringBuilder();
            while (index < formulaList.size()
                    && Character.isDigit(formulaList.get(index))) {
                atomNumberBuilder.append(formulaList.get(index));
                index++;
            }
            final int atomNumber = atomNumberBuilder.toString().equals("") ? 1 : Integer.parseInt(atomNumberBuilder.toString());
            return Map.of(atomName, atomNumber);
        }
        throw new RuntimeException();
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
}