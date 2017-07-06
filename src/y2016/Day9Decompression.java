package y2016;

import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static int decompressedLength2(String input) {
        List<Match> matches = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(input);

        while (matcher.find()) {
            matches.add(new Match(matcher));
        }

        if (matches.isEmpty()) {
            return input.length();
        }
        else {
            return helper2(matches, 0, 0, input.length());
        }
    }

    static int helper2(List<Match> matches, int matchIndex, int start, int end) {
        int length = 0;
        Match rootMatch = matches.get(matchIndex);
        int position = rootMatch.sequenceStart;
        length += (rootMatch.matchStart - start);  // Alpha characters before match

        for (int m = matchIndex + 1; m < matches.size(); m++) {

            Match match = matches.get(m);
            if (match.sequenceStart < end) {
                length += rootMatch.repeats * helper2(matches, m, position, rootMatch.sequenceEnd);
                position = match.sequenceEnd;
            }
            else {
                break;
            }
        }

        if (position < rootMatch.sequenceEnd) {
            length += (rootMatch.sequenceEnd - position) * rootMatch.repeats;
            position = rootMatch.sequenceEnd;
        }
        length += end - position;

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

    static int decompressedLength2(StringProvider input) throws Exception {
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
