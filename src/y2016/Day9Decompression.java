package y2016;

import util.StringProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day9Decompression {
    public static int decompressedLength(String input) {
        Pattern pattern = Pattern.compile("\\((\\d+)x(\\d+)\\)");
        int inputPosition = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.start() < inputPosition) {
                continue;
            }

            output.append(input, inputPosition, matcher.start());

            int length = parseInt(matcher.group(1));
            int repeats = parseInt(matcher.group(2));
            String sequence = input.substring(matcher.end(), matcher.end() + length);

            for (int r = 0; r < repeats; r++) {
                output.append(sequence);
            }
            inputPosition = matcher.end() + length;
        }
        output.append(input, inputPosition, input.length());
        return output.length();
    }

    static int decompressedLength(StringProvider input) throws Exception {
        int length = 0;
        while (input.hasMore()) {
            length += decompressedLength(input.next());
        }
        return length;
    }

    static int decompressedLength3(StringProvider input) throws Exception {
        int length = 0;
        while (input.hasMore()) {
            length += decompressedLength2(input.next());
        }
        return length;
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Length 1: " + decompressedLength(StringProvider.forFile("2016Day9Input.txt")));
        System.out.println("Length 2: " + decompressedLength(StringProvider.forFile("2016Day9Input.txt")));
    }
}
