import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day17Containers {

    List<Integer> sizes = new ArrayList<>();

    public Day17Containers(StringProvider input) throws Exception {
        while (input.hasMore()) {
            sizes.add(Integer.valueOf(input.next()));
        }
    }

    public int numberOfCombinations(int required) {
        List<List<Integer>> validCombinations = new ArrayList<>();
        List<Integer> indices = IntStream.range(0, sizes.size()).boxed().collect(Collectors.toList());
        Permutor permutor = new Permutor(indices);

        permutor.permute(new Function<List<Integer>, Void>() {
            @Override
            public Void apply(List<Integer> indices) {
                int remaining = required;
                for (int i=0; i < indices.size(); i++) {
                    remaining -= sizes.get(indices.get(i));

                    if (remaining == 0) {
                        List<Integer> combination = new ArrayList<>(indices.subList(0, i + 1));
                        combination.sort(Integer::compare);

                        if (!validCombinations.contains(combination)) {
                            validCombinations.add(combination);
                        }
                    }

                    if (remaining <= 0) {
                        break;
                    }
                }
                return null;
            }
        });

        return validCombinations.size();
    }

    public static void main(String[] args) throws Exception {
        Day17Containers containers = new Day17Containers(StringProvider.forFile("Day17Input.txt"));
        System.out.println("A: " + containers.numberOfCombinations(150));
    }
}
