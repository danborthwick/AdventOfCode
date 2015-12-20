import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day20FactorDeliveries {

    public static int minHouseNumberReceivingAtLeast(int minDeliveries) {

        minDeliveries /= 10;
        List<Set<Integer>> factors = new ArrayList<>(minDeliveries + 1);
        for (int i=0; i <= minDeliveries; i++) {
            factors.add(new HashSet<>());
        }

        for (int p = 2; p < minDeliveries / 2; p++) {
            for (int i=p+p; i <= minDeliveries; i += p) {
                factors.get(i).add(p);
            }
        }

        for (int i=1; i <= minDeliveries; i++) {
            int factorSum = i + 1;  // Prime factors
            Set<Integer> iFactors = factors.get(i);
            for (Integer iFactor : iFactors) {
                factorSum += iFactor;
            }
            if (factorSum >= minDeliveries) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println("A: " + Day20FactorDeliveries.minHouseNumberReceivingAtLeast(36000000));
    }

}
