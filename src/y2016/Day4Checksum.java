package y2016;

import util.MutableInt;
import util.StringProvider;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day4Checksum {

    public static int isReal(String line) {
        Pattern pattern = Pattern.compile("([a-z\\-]+)-(\\d+)\\[([a-z]+)]");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.matches()) { return 0; }

        String encrypted = matcher.group(1);
        char[] chars = encrypted.toCharArray();
        String sectorId = matcher.group(2);
        String expectedChecksum = matcher.group(3);

        Map<Character, MutableInt> histogram = new HashMap<>();

        for (char c : chars) {
            if (c == '-') {
                continue;
            }
            else if (!histogram.containsKey(c)) {
                histogram.put(c, new MutableInt(1));
            }
            else {
                histogram.get(c).increment();
            }
        }

        StringBuilder builder = new StringBuilder();

        histogram.entrySet().stream().sorted(
                new Comparator<Map.Entry<Character, MutableInt>>() {
                    @Override
                    public int compare(Map.Entry<Character, MutableInt> entry1, Map.Entry<Character, MutableInt> entry2) {
                        if (entry1.getValue().value > entry2.getValue().value) {
                            return -1;
                        } else if (entry1.getValue().value < entry2.getValue().value) {
                            return 1;
                        } else {
                            return Character.compare(entry1.getKey(), entry2.getKey());
                        }
                    }
                }
        ).limit(5).map(entry -> entry.getKey()).forEach(c -> builder.append(c));

        String actualChecksum = builder.toString();

        if (expectedChecksum.equals(actualChecksum)) {
            int sector = parseInt(sectorId);
            String decrypted = decrypt(encrypted, sector);
            if (decrypted.contains("north")) {
                System.out.println(decrypted + " " + sector);
            }
            return sector;
        }
        else {
            return 0;
        }
    }

    static int sectorSum(StringProvider input) throws Exception {
        int sum = 0;
        while (input.hasMore()) {
            sum += isReal(input.next());
        }
        return sum;
    }

    static String decrypt(String chars, int sectorId) {
        StringBuilder builder = new StringBuilder();
        int rotate = sectorId % 26;

        for (char c : chars.toCharArray()) {
            switch (c) {
                case '-':
                    builder.append(" ");
                    break;

                    default:
                        int in = c - 'a';
                        int rotatedInt = (in + rotate) % 26;
                        builder.append((char)('a' + rotatedInt));
                        break;
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day4Input.txt");
        System.out.println("Sector sum is " + Day4Checksum.sectorSum(input));

    }
}
