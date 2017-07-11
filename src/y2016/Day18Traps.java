package y2016;

public class Day18Traps {

    public static final char TRAP = '^';
    private static final char SAFE = '.';
    private final String firstRow;
    private final int rowCount;

    Day18Traps(String firstRow, int rowCount) {
        this.firstRow = firstRow;
        this.rowCount = rowCount;
    }

    static String nextRow(String prev) {
        StringBuilder builder = new StringBuilder(prev.length());
        for (int x=0; x < prev.length(); x++) {
            boolean left = (x >= 1) && (prev.charAt(x - 1) == TRAP);
            boolean center = prev.charAt(x) == TRAP;
            boolean right = (x < (prev.length() - 1)) && (prev.charAt(x + 1) == TRAP);

            boolean isTrap = (left && center && !right)
                    || (center && right && !left)
                    || (left && !center && !right)
                    || (!left && !center && right);

            builder.append(isTrap ? TRAP : SAFE);
        }
        return builder.toString();
    }

    int safeCount() {
        int count = countSafeSpaces(firstRow);
        String prev = firstRow;
        for (int row  = 1; row < rowCount; row++) {
            String next = nextRow(prev);
            count += countSafeSpaces(next);
            prev = next;
        }

        return count;
    }

    private int countSafeSpaces(String row) {
        return row.chars().reduce(0, (count, c) -> count += (c == SAFE) ? 1 : 0);
    }

    public static void main(String[] args) {
        String firstRow = "...^^^^^..^...^...^^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^^^.^^......^^^^";
        Day18Traps part1 = new Day18Traps(firstRow, 40);
        System.out.println("Safe spaces 1: " + part1.safeCount());

        Day18Traps part2 = new Day18Traps(firstRow, 400000);
        System.out.println("Safe spaces 2: " + part2.safeCount());
    }
}
