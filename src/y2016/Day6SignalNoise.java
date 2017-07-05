package y2016;

import util.StringProvider;
import util.Histogram;

import java.util.List;
import java.util.stream.Collectors;

public class Day6SignalNoise {
    public static String decode(StringProvider input) throws Exception {
        List<String> lines = input.asList();

        int length = lines.get(0).length();
        StringBuilder builder = new StringBuilder(length);
        for (int i=0; i < length; i++) {
            final int pos = i;
            List<Character> chars = lines.stream().map(line -> line.charAt(pos)).collect(Collectors.toList());
            char mostCommon = Histogram.mostCommon(chars);
            builder.append(mostCommon);
        }
        return builder.toString();
    }

    public static String decodeLeast(StringProvider input) throws Exception {
        List<String> lines = input.asList();

        int length = lines.get(0).length();
        StringBuilder builder = new StringBuilder(length);
        for (int i=0; i < length; i++) {
            final int pos = i;
            List<Character> chars = lines.stream().map(line -> line.charAt(pos)).collect(Collectors.toList());
            char leastCommon = Histogram.leastCommon(chars);
            builder.append(leastCommon);
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Decoded: " + decode(makeInput()));
        System.out.println("Decoded least: " + decodeLeast(makeInput()));
    }

    private static StringProvider makeInput() throws Exception {
        return StringProvider.forFile("2016Day6Input.txt");
    }
}
