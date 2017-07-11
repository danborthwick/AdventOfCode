package y2016;

import java.util.Arrays;

public class Day16DragonCode {
    public static String code(String inputString) {
        char[] input = inputString.toCharArray();
        char[] output = Arrays.copyOf(input, (2 * input.length) + 1);
        output[input.length] = '0';

        for (int i=0; i < input.length; i++) {
            output[output.length - 1 - i] = ((input[i] == '1') ? '0' : '1');
        }
        return new String(output);
    }

    public static String checksum(String inputString) {
        char[] checksum = inputString.toCharArray();
        int sumLength = checksum.length;

        do {
            sumLength /= 2;

            for (int i=0; i < sumLength; i++) {
                if (checksum[i * 2] == checksum[(i * 2) + 1]) {
                    checksum[i] = '1';
                }
                else {
                    checksum[i] = '0';
                }
            }
        } while (sumLength % 2 == 0);

        return new String(checksum, 0, sumLength);
    }

    public static String fillDisk(int diskSize, String state) {
        while (state.length() < diskSize) {
            state = code(state);
        }
        state = state.substring(0, diskSize);
        return checksum(state);
    }

    public static void main(String[] args) {
        System.out.println("Sum for 272 is: " + Day16DragonCode.fillDisk(272, "10111011111001111"));
        System.out.println("Sum for 35651584 is: " + Day16DragonCode.fillDisk(35651584, "10111011111001111"));
    }
}
