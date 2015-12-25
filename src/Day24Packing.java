import java.util.ArrayList;
import java.util.List;

public class Day24Packing {

    private final List<Integer> weights;
    private final int totalWeight;

    public Day24Packing(StringProvider input) throws Exception {
        weights = new ArrayList<>();
        while (input.hasMore()) {
            weights.add(Integer.valueOf(input.next()));
        }
        weights.sort(Integer::compare);

        totalWeight =  weights.stream().mapToInt(Integer::intValue).sum();
    }

    public long quantumEntanglement(final int compartments) {

        final int weightPerCompartment = totalWeight / compartments;
        final int maxPassengerCompartmentSize = weights.size() / compartments;

        Chooser<Integer> chooser = new Chooser<>(weights);
        final List<List<Integer>> passengerConfigurations = new ArrayList();

        for (int size = 1; (size <= maxPassengerCompartmentSize) && (passengerConfigurations.isEmpty()); size++) {
            chooser.choose(size, l -> {
                if (hasWeight(l, weightPerCompartment)) {
                    passengerConfigurations.add(new ArrayList<>(l));
                }
                return null;
            });
        }

        passengerConfigurations.sort((a, b) -> Long.compare(entanglement(a), entanglement(b)));

        return entanglement(passengerConfigurations.get(0));
    }

    private boolean hasWeight(List<Integer> configuration, int weightPerCompartment) {
        return configuration.stream().mapToInt(Integer::valueOf).sum() == weightPerCompartment;
    }

    private long entanglement(List<Integer> configuration) {
        return configuration.stream().mapToLong(Long::valueOf).reduce(1, (a, b) -> a * b);
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day24Input.txt");
        Day24Packing packing = new Day24Packing(input);
        System.out.println("For 3 compartments: " + packing.quantumEntanglement(3));
        System.out.println("For 4 compartments: " + packing.quantumEntanglement(4));
    }
}
