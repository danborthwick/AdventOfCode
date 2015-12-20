public class Day20FactorDeliveries {

    public static int minHouseNumberReceivingAtLeast(int minDeliveries) {

        minDeliveries /= 10;
        int[] factorSums = new int[minDeliveries + 1];
        for (int i=2; i <= minDeliveries; i++) {
            factorSums[i] = i + 1;
        }

        for (int p = 2; p < minDeliveries / 2; p++) {
            for (int i=p+p; i <= minDeliveries; i += p) {
                factorSums[i] += p;
            }
        }

        for (int i=2; i < minDeliveries; i++) {
            if (factorSums[i] >= minDeliveries) {
                return i;
            }
        }

        return -1;
    }

    public static int minHouseNumberReceivingAtLeastB(int minDeliveries) {

        minDeliveries /= 11;
        int[] factorSums = new int[minDeliveries + 1];
        for (int i=2; i <= minDeliveries; i++) {
            factorSums[i] = i;
        }

        for (int p = 1; p < minDeliveries / 2; p++) {
            int iMax = Math.min(p * 50, minDeliveries);
            for (int i=p+p; i <= iMax; i += p) {
                factorSums[i] += p;
            }
        }

        for (int i=2; i < minDeliveries; i++) {
            if (factorSums[i] >= minDeliveries) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println("A: " + Day20FactorDeliveries.minHouseNumberReceivingAtLeast(36000000));
        System.out.println("B: " + Day20FactorDeliveries.minHouseNumberReceivingAtLeastB(36000000));
    }

}
