package y2015;

import util.StringProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16AuntSue {

    static final String[] propertyNames = {
            "children",
            "cats",
            "samoyeds",
            "pomerians",
            "akitas",
            "vizslas",
            "goldfish",
            "trees",
            "cars",
            "perfumes"
    };

    static class Aunt {

        public final Map<String, Integer> knownProperties = new HashMap<>();
        public final int id;

        static Pattern idPattern = Pattern.compile("Sue (\\-?\\d+)");
        static Pattern propertyPattern = Pattern.compile("(?<name>\\w+): (?<amount>\\d+)");

        public Aunt(String line) {
            Matcher idMatcher = idPattern.matcher(line);
            idMatcher.find();
            id = Integer.valueOf(idMatcher.group(1));

            Matcher propertyMatcher = propertyPattern.matcher(line);
            while (propertyMatcher.find()) {
                knownProperties.put(propertyMatcher.group("name"), Integer.valueOf(propertyMatcher.group("amount")));
            }
        }

        public boolean matchesA(Aunt spec) {

            for (String propertyName : propertyNames) {
                if (knownProperties.containsKey(propertyName)
                        && knownProperties.get(propertyName) != spec.knownProperties.get(propertyName)) {
                    return false;
                }
            }

            return true;
        }

        public boolean matchesB(Aunt spec) {

            for (String propertyName : propertyNames) {
                if (knownProperties.containsKey(propertyName)) {

                    int thisValue = this.knownProperties.get(propertyName);
                    int specValue = spec.knownProperties.get(propertyName);

                    switch (propertyName) {
                        case "cats":
                        case "trees":
                            if (thisValue <= specValue)
                                return false;
                            break;

                        case "pomeranians":
                        case "goldfish":
                            if (thisValue >= specValue)
                                return false;
                            break;

                        default:
                            if (thisValue != specValue)
                                return false;
                            break;
                    }
                }
            }

            return true;
        }
    }

    private final List<Aunt> aunts = new ArrayList<>();

    public Day16AuntSue(StringProvider input) throws Exception {
        while (input.hasMore()) {
            aunts.add(new Aunt(input.next()));
        }
    }

    private int sueIdMatchingSpecA(Aunt spec) throws Exception {
        return aunts.stream().filter(aunt -> aunt.matchesA(spec)).findFirst().get().id;
    }

    private int sueIdMatchingSpecB(Aunt spec) throws Exception {
        return aunts.stream().filter(aunt -> aunt.matchesB(spec)).findFirst().get().id;
    }

    static String SUE_SPEC_A = "Sue -1: children: 3 cats: 7 samoyeds: 2 pomeranians: 3 akitas: 0 vizslas: 0 goldfish: 5 trees: 3 cars: 2 perfumes: 1";

    public static void main(String[] args) throws Exception
    {
        StringProvider input = StringProvider.forFile("Day16Input.txt");
        Day16AuntSue day16AuntSue = new Day16AuntSue(input);
        Aunt spec = new Aunt(SUE_SPEC_A);

        System.out.println("Sue A: " + day16AuntSue.sueIdMatchingSpecA(spec));
        System.out.println("Sue A: " + day16AuntSue.sueIdMatchingSpecB(spec));
    }
}
