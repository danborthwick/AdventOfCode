import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day17Containers {

    List<Integer> sizes = new ArrayList<>();
    Map<Integer, Integer> mapNumContainersToPermutations = new HashMap<>();

    public Day17Containers(StringProvider input) throws Exception {
        while (input.hasMore()) {
            sizes.add(Integer.valueOf(input.next()));
        }
    }

    public int numberOfCombinations(int required) {
        int result = 0;

        int max = 1 << sizes.size();
        for (int combination = 0; combination < max; combination++) {
            if (validCombination(combination, required)) {
                result++;
            }
        }

        return result;
    }

    private boolean validCombination(int combination, int required) {
        int actualTotal = 0;
        int containersUsed = 0;

        for (int i=0; i < sizes.size(); i++) {
            if ((combination & (1 << i)) != 0) {
                actualTotal += sizes.get(i);
                containersUsed++;
            }
        }

        boolean matched = actualTotal == required;
        if (matched) {
            mapNumContainersToPermutations.put(containersUsed, mapNumContainersToPermutations.getOrDefault(containersUsed, 0) + 1);
        }

        return matched;
    }

    public int permutationsOfMinimumContainersUsed() {
        Stream<Integer> stream = mapNumContainersToPermutations.keySet().stream();
        return mapNumContainersToPermutations.get(stream.sorted().findFirst().get());
    }

    public static void main(String[] args) throws Exception {
        Day17Containers containers = new Day17Containers(StringProvider.forFile("Day17Input.txt"));
        System.out.println("Total permutations: " + containers.numberOfCombinations(150));
        System.out.println("Permutations of min: " + containers.permutationsOfMinimumContainersUsed());
    }
}
