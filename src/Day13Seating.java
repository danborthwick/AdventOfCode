import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Seating {

    Map<String, Map<String, Integer>> dinerMap;

    public Day13Seating(StringProvider input) throws Exception {
        dinerMap = new HashMap();

        while (input.hasMore()) {
            addToMap(input.next());
        }
    }

    static Pattern pattern = Pattern.compile("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+)\\.");

    private void addToMap(String line) {
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String firstDiner = matcher.group(1);
        String secondDiner = matcher.group(4);
        String gainLose = matcher.group(2);
        Integer amount = Integer.valueOf(matcher.group(3));

        if (gainLose.equals("lose")) {
            amount = -amount;
        }

        Map<String, Integer> entry = dinerMap.get(firstDiner);
        if (entry == null) {
            entry = new HashMap();
            dinerMap.put(firstDiner, entry);
        }

        entry.put(secondDiner, amount);
    }

    public int bestHappiness() {
        List<String> diners = getDiners();

        Permutor<String> permutor = new Permutor(diners);
        final MutableInt bestHappiness = new MutableInt(Integer.MIN_VALUE);

        Function<List<String>, Void> f = new Function<List<String>, Void>() {
            @Override
            public Void apply(List<String> arrangement) {
                int arrangementHappiness = happinessOfArrangement(arrangement);
                if (arrangementHappiness > bestHappiness.value)
                    bestHappiness.value = arrangementHappiness;

                return null;
            }
        };

        permutor.permute(f);

        return bestHappiness.value;
    }

    public List<String> getDiners() {
        List<String> diners = new ArrayList();
        diners.addAll(dinerMap.keySet());
        return diners;
    }

    private int happinessOfArrangement(List<String> arrangement) {
        int happiness = 0;

        for (int i=0; i < arrangement.size(); i++) {
            String firstDiner = arrangement.get(i);
            String secondDiner = arrangement.get((i + 1) % arrangement.size());

            happiness += dinerMap.get(firstDiner).get(secondDiner);
            happiness += dinerMap.get(secondDiner).get(firstDiner);
        }

        return happiness;
    }

    private void addMe() {
        dinerMap.put("Me", new HashMap());
        for (String diner : getDiners()) {
            dinerMap.get(diner).put("Me", 0);
            dinerMap.get("Me").put(diner, 0);
        }

    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day13Input.txt");
        System.out.println("A: " + new Day13Seating(input).bestHappiness());

        input = StringProvider.forFile("Day13Input.txt");
        Day13Seating day13SeatingB = new Day13Seating(input);
        day13SeatingB.addMe();
        System.out.println("B: " + day13SeatingB.bestHappiness());
    }
}
