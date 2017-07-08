package y2016;

import util.StringProvider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Integer.parseInt;

public class Day9Decompression {

    public static final Pattern PATTERN = Pattern.compile("\\((\\d+)x(\\d+)\\)");

    public static int decompressedLength(String input) {
        int inputPosition = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = PATTERN.matcher(input);

        while (matcher.find()) {
            if (matcher.start() < inputPosition) {
                continue;
            }

            output.append(input, inputPosition, matcher.start());

            Match match = new Match(matcher);
            String sequence = input.substring(match.sequenceStart, match.sequenceStart + match.length);

            for (int r = 0; r < match.repeats; r++) {
                output.append(sequence);
            }
            inputPosition = match.sequenceStart + match.length;
        }
        output.append(input, inputPosition, input.length());
        return output.length();
    }

    static long decompressedLength2(String input) {
        List<Long> counts = LongStream.range(0, input.length()).mapToObj(i -> 1L).collect(Collectors.toList());
        Matcher matcher = PATTERN.matcher(input);

        while (matcher.find()) {
            Match match = new Match(matcher);
            for (int i = match.sequenceStart; i < match.sequenceEnd; i++) {
                counts.set(i, counts.get(i) * match.repeats);
            }
        }

        long length = 0;
        for (int i=0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ('A' <= c && c <= 'Z') {
                length += counts.get(i);
            }
        }
        return length;
    }

    static class Match {
        public int sequenceStart;
        public int sequenceEnd;
        public int matchStart;
        public int length;
        public int repeats;

        public Match(Matcher matcher) {
            length = parseInt(matcher.group(1));
            repeats = parseInt(matcher.group(2));
            matchStart = matcher.start();
            sequenceStart = matcher.end();
            sequenceEnd = sequenceStart + length;
        }
    }


    static int decompressedLength(StringProvider input) throws Exception {
        int length = 0;
        while (input.hasMore()) {
            length += decompressedLength(input.next());
        }
        return length;
    }

    static long decompressedLength2(StringProvider input) throws Exception {
        long length = 0;
        while (input.hasMore()) {
            length += decompressedLength2(input.next());
        }
        return length;
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Length 1: " + decompressedLength(StringProvider.forFile("2016Day9Input.txt")));
        System.out.println("Length 2: " + decompressedLength2(StringProvider.forFile("2016Day9Input.txt")));
    }
}
