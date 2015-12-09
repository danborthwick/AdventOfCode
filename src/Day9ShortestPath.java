import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9ShortestPath {

    List<List<Integer>> fromToDist = new ArrayList();
    private final Map<String, Integer> nameToId = new HashMap();

    Day9ShortestPath(StringProvider input) throws Exception {
        Pattern pattern = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

        while (input.hasMore()) {
            String line = input.next();
            Matcher matcher = pattern.matcher(line);
            matcher.find();

            int from = idFromName(matcher.group(1), nameToId);
            int to = idFromName(matcher.group(2), nameToId);
            Integer distance = Integer.valueOf(matcher.group(3));

            set(from, to, distance);
        }
    }

    private void set(int from, int to, Integer distance) {
        List<Integer> fromList = fromToDist.get(from);
        while (fromList.size() <= to) {
            fromList.add(0);
        }
        fromList.set(to, distance);
    }

    private int idFromName(String name, Map<String, Integer> nameToId) {
        Integer id = nameToId.get(name);
        if (id == null) {
            id = fromToDist.size();
            nameToId.put(name, id);
            fromToDist.add(new ArrayList<>());
        }
        return id;
    }

    private int lengthOfPath(List<Integer> order) {
        int length = 0;

        for (int from=0; from < order.size() - 1; from++) {
            int to = from + 1;
            length += fromToDist.get(from).get(to);
        }

        return length;
    }

    class MutableInt {
        public int value;

        public MutableInt(int value) {
            this.value = value;
        }
    }

    public int shortestPath() {
        List<Integer> visitOrder = new ArrayList(nameToId.size());
        for (int i=0; i < nameToId.size(); i++) {
            visitOrder.add(i);
        }

        final MutableInt shortestFound = new MutableInt(Integer.MAX_VALUE);

        new Permutor(visitOrder).apply((order) -> {
            shortestFound.value = Math.min(shortestFound.value, lengthOfPath(order));
            return null;
        });

        return shortestFound.value;
    }
}
