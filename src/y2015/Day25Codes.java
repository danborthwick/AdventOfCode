package y2015;

import java.util.ArrayList;
import java.util.List;

public class Day25Codes {
    public static long code(int row, int column) {
        int maxRow = row + column - 1;

        List<List<Long>> table = new ArrayList<>(maxRow + 1);
        table.add(new ArrayList<>());
        table.add(new ArrayList<>());
        table.get(1).add(0L);
        table.get(1).add(20151125L);

        for (int y = 2; y <= maxRow; y++) {
            table.add(new ArrayList<>(maxRow + 1));
            table.get(y).add(0L);
            table.get(y).add(next(table.get(1).get(y - 1)));

            for (int i = 1; i < y; i++) {
                table.get(y - i).add(next(table.get(y - i + 1).get(i)));
            }
        }

        return table.get(row).get(column);
    }

    public static long next(long l) {
        // Multiply it by 252533 to get 5088824049625. Then, divide that by 33554393, which leaves a remainder of 31916031
        l *= 252533L;
        return l % 33554393L;
    }

    public static void main(String[] args) {
        System.out.println("code at row 3010, column 3019: " + Day25Codes.code(3010, 3019));
    }
}
