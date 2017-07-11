package y2016;

import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14OneTimePad {

    private String salt;
    private int stretch;
    private final MessageDigest md5;
    private byte[] digits;

    static final byte[] intToChar = new byte[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    public Day14OneTimePad(String salt, int stretch) throws Exception {
        this.salt = salt;
        this.stretch = stretch;
        md5 = MessageDigest.getInstance("MD5");
        digits = new byte[32];
    }

    public int indexOfKey(int targetKeyIndex) {
        List<Integer> keyList = getKeys(targetKeyIndex);
        return keyList.get(targetKeyIndex - 1);
    }

    List<Integer> getKeys(int keyCount) {
        Map<Byte, Set<Integer>> triplesForByte = new HashMap(16);
        for (byte b : intToChar) {
            triplesForByte.put(b, new HashSet<>());
        }

        SortedSet<Integer> keys = new TreeSet<>();
        int maxIndex = Integer.MAX_VALUE;

        for (int index = 0; index < maxIndex; index++) {
            getDigits(index);

            byte prev2 = digits[0];
            byte prev = digits[1];
            boolean firstTripleInIndex = true;

            for (int b = 2; b < digits.length; b++) {
                byte next = digits[b];
                if ((next == prev) && (next == prev2)) {
                    // Triple found
                    Set<Integer> triples = triplesForByte.get(next);

                    if ((b >= 4) && (next == digits[b - 3]) && (next == digits[b - 4])) {
                        // Quintuple found
                        for (Iterator<Integer> iterator = triples.iterator(); iterator.hasNext(); ) {
                            int tripleIndex = iterator.next();
                            int diff = index - tripleIndex;
                            if (diff <= 1000) {
                                if (diff > 0) {
                                    keys.add(tripleIndex);
                                    if (keys.size() == keyCount) {
                                        maxIndex = index + 1001;
                                    }
                                }
                            }
                            else {
                                iterator.remove();
                            }
                        }
                    }

                    if (firstTripleInIndex) {
                        triples.add(index);
                        firstTripleInIndex = false;
                    }
                }

                prev2 = prev;
                prev = next;
            }
        }

        return keys.stream().collect(Collectors.toList());
    }

    private void getDigits(int index) {
        String message = salt + index;
        byte[] bytes = message.getBytes();
        for (int s=0; s <= stretch; s++) {
            bytes = md5.digest(bytes);
            for (int d = 0; d < 16; d++) {
                digits[d * 2] = intToChar[(bytes[d] & 0xf0) >> 4];
                digits[d * 2 + 1] = intToChar[bytes[d] & 0x0f];
            }
            bytes = digits;
        }
    }



    int indexOfKeyNaiive(int targetKeyIndex) {
        List<Integer> keys = getKeysNaiive(targetKeyIndex);
        return keys.get(targetKeyIndex - 1);
    }

    List<Integer> getKeysNaiive(int keyCount) {
        Pattern triplePattern = Pattern.compile("([1-9a-f])\\1\\1");
        Pattern quintPattern = Pattern.compile("([1-9a-f])\\1\\1\\1\\1");

        List<Integer> keys = new ArrayList<>(keyCount);

        for (int index = 0; keys.size() < keyCount; index++) {
            getDigits(index);
            String digitString = new String(digits);
            Matcher tripleMatcher = triplePattern.matcher(digitString);
            if (tripleMatcher.find()) {
                String matchedChar = tripleMatcher.group(1);

                for (int qIndex = index + 1; qIndex <= index + 1000; qIndex++) {
                    getDigits(qIndex);
                    String qDigitString = new String(digits);
                    Matcher qMatcher = quintPattern.matcher(qDigitString);
                    if (qMatcher.find() && qMatcher.group(1).equals(matchedChar)) {
                        keys.add(index);
                    }
                }
            }
        }
        return keys;
    }

    public static void main(String[] args) throws Exception {
        Day14OneTimePad pad = new Day14OneTimePad("jlmsuwbz", 0);
        System.out.println("P1: " + pad.indexOfKey(64));
        Day14OneTimePad stretched = new Day14OneTimePad("jlmsuwbz", 2016);
        System.out.println("Stretched: " + stretched.indexOfKey(64));
    }
}
