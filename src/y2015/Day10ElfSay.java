package y2015;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day10ElfSay {

    private static final String INPUT_A = "1113222113";

    public static String elfSay(String input, int iterations) {
        for (int i=0; i < iterations; i++) {
            input = encode(input);
        }
        return input;
    }

    private static String encode(String input) {
        StringBuilder output = new StringBuilder();

        for (int start = 0; start < input.length(); ) {
            int end = start;
            char digit = input.charAt(start);
            while(end < input.length() && input.charAt(end) == digit) {
                end++;
            }
            int count = end - start;
            output.append(count).append(digit);

            start = end;
        }
        return output.toString();
    }

    private static String decode(String input) {
        StringBuilder output = new StringBuilder();

        for (int pos = 0; pos < input.length() - 1; pos += 2) {
            Integer count = Integer.parseInt(input.substring(pos, pos + 1));
            char digit = input.charAt(pos + 1);

            for (int i=0; i < count; i++) {
                output.append(digit);
            }
        }

        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println("40 times: " + elfSay(INPUT_A, 40).length());
        System.out.println("50 times: " + elfSay(INPUT_A, 50).length());
    }

    @Test
    public void test1() {
        assertThat(elfSay("211", 1), is("1221"));
        assertThat(elfSay("111221", 1), is("312211"));
    }
}
