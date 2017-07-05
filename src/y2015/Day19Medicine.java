package y2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Medicine {

    static final Pattern pattern = Pattern.compile("(\\w+) => (\\w+)");
    private String medicineMolecule;

    class Replacement {

        final Pattern fromPattern;
        private final Pattern toPattern;
        final String to;
        private final String from;

        Replacement(String from, String to) {
            this.fromPattern = Pattern.compile(from);
            this.toPattern = Pattern.compile(to);
            this.to = to;
            this.from = from;
        }

    }
    List<Replacement> replacements = new ArrayList<>();

    public Day19Medicine(StringProvider input) throws Exception {
        while (input.hasMore()) {
            String line = input.next();
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String fromString = matcher.group(1);
                replacements.add(new Replacement(fromString, matcher.group(2)));
            }
            else {
                medicineMolecule = line;
            }
        }
    }

    public int possibilities() {

        Set<String> results = new HashSet<>();

        for (Replacement replacement : replacements) {
            Matcher matcher = replacement.fromPattern.matcher(medicineMolecule);
            while (matcher.find()) {
                String newMolecule = makeMolecule(matcher, medicineMolecule, replacement.to);
                results.add(newMolecule);
            }
        }

        return results.size();
    }

    public String makeMolecule(Matcher matcher, String input, String replacement) {
        String newMolecule = input.substring(0, matcher.start());
        newMolecule += replacement;
        newMolecule += input.substring(matcher.end());
        return newMolecule;
    }

    public int stepsFromETo(String molecule) {
        int totalElements = matchCount(molecule, "[A-Z][a-z]?");
        int rnArMatches = matchCount(molecule, "Ar|Rn");
        int yMatches = matchCount(molecule, "Y");
        return totalElements - rnArMatches - (2 * yMatches) - 1;
    }

    private int matchCount(String input, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day19Input.txt");
        Day19Medicine medicine = new Day19Medicine(input);
        System.out.println("A: " + medicine.possibilities());
        System.out.println("B: " + medicine.stepsFromETo(medicine.medicineMolecule));
    }
}
