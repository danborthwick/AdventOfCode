package y2016;

import util.StringProvider;

public class Day2NumPad {
    static String code(StringProvider input) throws Exception {
        return codeHelper(input, 2, 2, Day2NumPad.numPad1);
    }

    static String code2(StringProvider input) throws Exception {
        return codeHelper(input, 1, 3, Day2NumPad.numPad2);
    }

    private static String codeHelper(StringProvider input, int x, int y, String[] pad) throws Exception {
        StringBuilder code = new StringBuilder();

        while (input.hasMore()) {
            char[] line = input.next().toCharArray();
            for (char direction : line) {
                int xBefore = x, yBefore = y;

                switch (direction) {
                    case 'U':
                        y--;
                        break;
                    case 'D':
                        y++;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'R':
                        x++;
                        break;
                }
                if (digit(pad, x, y) == '0') {
                    x = xBefore;
                    y = yBefore;
                }
            }
            char number = digit(pad, x, y);
            code.append(number);
        }

        return code.toString();
    }

    static char digit(String[] pad, int x, int y) {
        return pad[y].charAt(x);
    }

    static String[] numPad1 = new String[]{
            "00000",
            "01230",
            "04560",
            "07890",
            "00000",
    };

    static String[] numPad2 = new String[] {
            "0000000",
            "0001000",
            "0023400",
            "0567890",
            "00ABC00",
            "000D000",
            "0000000",
    };

    public static void main(String[] args) throws Exception {
        StringProvider input = getInput();
        System.out.println("Code is " + Day2NumPad.code(input));
        input = getInput();
        System.out.println("Code is " + Day2NumPad.code2(input));
    }

    private static StringProvider getInput() throws Exception {
        return StringProvider.forFile("2016Day2Input.txt");
    }
}
