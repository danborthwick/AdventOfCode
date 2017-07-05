package y2016;

import util.StringProvider;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class Day3Squares {
    public static void main(String[] args) throws Exception {
        System.out.println("Possible triangles " + Day3Squares.countPossible(getInput()));
        System.out.println("Vertical triangles " + Day3Squares.countPossibleVertical(getInput()));
    }

    private static StringProvider getInput() throws Exception {
        return StringProvider.forFile("2016Day3Input.txt");
    }

    static int countPossible(StringProvider input) throws Exception {
        int count = 0;
        while (input.hasMore()) {
            String line = input.next();
            if (isPossible(line)) {
                count++;
            }
        }
        return count;
    }

    static int countPossibleVertical(StringProvider input) throws Exception {
        int[][] sides = new int[3][3];
        int count = 0;

        while (input.hasMore()) {
            for (int i=0; i < 3; i++) {
                String line = input.next();
                String[] sideStrings = line.split("\\s+");
                sides[i] = Arrays.stream(sideStrings).skip(1).mapToInt(s -> parseInt(s)).toArray();
            }

            for (int x = 0; x < 3; x++) {
                if (isPossible(String.format("  %d  %d  %d", sides[0][x], sides[1][x], sides[2][x]))) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPossible(String line) {
        String[] sideStrings = line.split("\\s+");
        int[] sides = Arrays.stream(sideStrings).skip(1).mapToInt(s -> parseInt(s)).sorted().toArray();
        return (sides[0] + sides[1]) > sides[2];
    }
}
