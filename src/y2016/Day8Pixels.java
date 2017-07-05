package y2016;

import util.StringProvider;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day8Pixels {

    public static final Pattern RECT_PATTERN = Pattern.compile("rect (\\d+)x(\\d+)");
    public static final Pattern COLUMN_PATTERN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
    public static final Pattern ROW_PATTERN = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final char SET = '#';
    public static final char UNSET = '.';

    public static void step(String instruction, char[][] screen) throws Exception {
        Matcher matcher = RECT_PATTERN.matcher(instruction);
        if (matcher.matches()) {
            rect(screen, parseInt(matcher.group(1)), parseInt(matcher.group(2)));
            return;
        }

        matcher = COLUMN_PATTERN.matcher(instruction);
        if (matcher.matches()) {
            rotateColumn(screen, parseInt(matcher.group(1)), parseInt(matcher.group(2)));
            return;
        }

        matcher = ROW_PATTERN.matcher(instruction);
        if (matcher.matches()) {
            rotateRow(screen, parseInt(matcher.group(1)), parseInt(matcher.group(2)));
            return;
        }

        throw new Exception("Unknown instruction '" + instruction + "'");
    }

    private static void rect(char[][] screen, int columns, int rows) {
        for (int y=0; y < rows; y++) {
            for (int x=0; x < columns; x++) {
                screen[y][x] = SET;
            }
        }
    }

    private static void rotateColumn(char[][] screen, int x, int count) {
        for (int i=0; i < count; i++) {
            char swap = screen[screen.length - 1][x];

            for (int y = screen.length - 1; y >= 1; y--) {
                screen[y][x] = screen[y - 1][x];
            }
            screen[0][x] = swap;
        }
    }

    private static void rotateRow(char[][] screen, int y, int count) {
        for (int i=0; i < count; i++) {
            int width = screen[0].length;
            char swap = screen[y][width - 1];

            for (int x = width - 1; x >= 1; x--) {
                screen[y][x] = screen[y][x - 1];
            }
            screen[y][0] = swap;
        }
    }

    public static int countPixels(char[][] screen) {
        int count = 0;
        for (char[] row : screen) {
            for (char c : row) {
                if (c == SET) {
                    count++;
                }
            }
        }
        return count;
    }

    private static char[][] run(StringProvider input) throws Exception {
        char[][] screen = makeScreen(50 ,6);
        while (input.hasMore()) {
            step(input.next(), screen);
        }
        return screen;
    }

    static char[][] makeScreen(int width, int height) {
        char[][] screen = new char[height][];
        for (int y = 0; y < height; y++) {
            screen[y] = new char[width];
            Arrays.fill(screen[y], UNSET);
        }
        return screen;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day8Input.txt");
        char[][] screen = run(input);
        System.out.println("Count: " + countPixels(screen));

        for (char[] row : screen) {
            System.out.println(new String(row));
        }
    }
}
