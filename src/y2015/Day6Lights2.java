package y2015;

import util.StringProvider;

public class Day6Lights2 {
    public static int countLights() throws Exception {
        StringProvider input = StringProvider.forFile("Day6Input.txt");
        int[][] lights = new int[1000][1000];

        while (input.hasMore())   {
            apply(input.next(), lights);
        }

        return countOn(lights);
    }

    private static int countOn(int[][] lights) {
        int result = 0;
        for (int[] row : lights) {
            for (int light : row) {
                result += light;
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


    private static void apply(String instruction, int[][] lights) {
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

    private static void applyLight(int x, int y, int[][] lights, Instruction in) {
        switch (in) {
            case ON:
                lights[y][x]++;
                break;
            case OFF:
                lights[y][x] = Math.max(lights[y][x] - 1, 0);
                break;
            case TOGGLE:
                lights[y][x] += 2;
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Light2s: " + countLights());
    }
}
