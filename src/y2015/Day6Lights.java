package y2015;
import util.*;

public class Day6Lights {
    public static int countLights() throws Exception {
        StringProvider input = StringProvider.forFile("Day6Input.txt");
        boolean[][] lights = new boolean[1000][1000];
        for (boolean[] row : lights) {
            for (int x =0; x < row.length; x++) {
                row[x] = false;
            }
        }

        while (input.hasMore())   {
            apply(input.next(), lights);
        }

        return countOn(lights);
    }

    private static int countOn(boolean[][] lights) {
        int result = 0;
        for (boolean[] row : lights) {
            for (boolean light : row) {
                if (light) result++;
            }
        }

        return result;
    }

    enum Instruction {
        ON, OFF, TOGGLE
    }

    public static Instruction fromString(String s) {
        switch (s) {
            case "on":
                return Instruction.ON;
            case "off":
                return Instruction.OFF;
            case "toggle":
                return Instruction.TOGGLE;
        }
        return Instruction.TOGGLE;
    }


    private static void apply(String instruction, boolean[][] lights) {
        String[] parts = instruction.split("[ ,]");
        int  offset = parts[0].equals("turn") ? 1 : 0;
        int x0 = Integer.valueOf(parts[offset + 1]);
        int y0 = Integer.valueOf(parts[offset + 2]);
        int x1 = Integer.valueOf(parts[offset + 4]);
        int y1 = Integer.valueOf(parts[offset + 5]);

        Instruction in = fromString(parts[offset]);
        for (int x = x0; x <= x1; x++) {
            for (int y=y0; y <= y1; y++) {
                applyLight(x, y, lights, in);
            }
        }
    }

    private static void applyLight(int x, int y, boolean[][] lights, Instruction in) {
        switch (in) {
            case ON:
                lights[y][x] = true;
                break;
            case OFF:
                lights[y][x] = false;
                break;
            case TOGGLE:
                lights[y][x] = !lights[y][x];
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Lights: " + countLights());
    }
}
