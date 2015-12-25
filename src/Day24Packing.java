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

    public int quantumEntanglement() {

        final int weightPerCompartment = totalWeight / 3;
        final int maxPassengerCompartmentSize = weights.size() / 3;

        Chooser<Integer> chooser = new Chooser<>(weights);
        final List<Integer>[] passengerConfiguration = new List[] { null };

        for (int size = 1; (size <= maxPassengerCompartmentSize) && (passengerConfiguration[0] == null); size++) {
            chooser.choose(size, l -> {
                if (hasWeight(l, weightPerCompartment)) {
                    passengerConfiguration[0] = new ArrayList<>(l);
                }
                return null;
            });
        }



        return -1;
    }

    private boolean hasWeight(List<Integer> configuration, int weightPerCompartment) {
        return configuration.stream().mapToInt(Integer::valueOf).sum() == weightPerCompartment;
    }
}
