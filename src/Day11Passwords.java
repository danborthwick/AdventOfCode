import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Day11Passwords {

    public static String nextPassword(String lastPassword) {

        char[] chars = getChars(lastPassword);

        while (true) {
            increment(chars);
            if (isValid(chars)) {
                return String.copyValueOf(chars);
            }
        }
    }

    private static boolean isValid(char[] chars) {
        return hasStraight(chars) && !hasIllegalChars(chars) && hasTwoPairs(chars);
    }

    private static boolean hasStraight(char[] chars) {
        for (int i=0; i < chars.length - 2; i++) {
            if ((chars[i + 1] - chars[i] == 1) && (chars[i + 2] - chars[i + 1] == 1))
                return true;
        }
        return false;
    }

    private static boolean hasIllegalChars(char[] chars) {
        for (char c : chars) {
            if (c == 'i' || c == 'l' || c == 'o')
                return true;
        }
        return false;
    }

    private static boolean hasTwoPairs(char[] chars) {
        int pairCount = 0;
        char lastPairChar = '0';
        for (int i=0; i < chars.length - 1; i++) {
            if ((chars[i] == chars[i + 1]) && (chars[i] != lastPairChar)) {
                lastPairChar = chars[i];
                pairCount++;
                i++;
            }
        }
        return pairCount >= 2;
    }

    private static void increment(char[] chars) {
        boolean carry = true;
        for (int digit = chars.length - 1; digit >= 0 && carry; digit--) {
            carry = ++chars[digit] > 'z';
            if (carry) {
                chars[digit] = 'a';
            }
        }
    }

    public static char[] getChars(String s) {
        char[] chars = new char[s.length()];
        s.getChars(0, s.length(), chars, 0);
        return chars;
    }

    @Test
    public void testIsValid() {
        assertThat(isValid(getChars("hijklmmn")), is(false));
        assertThat(isValid(getChars("abbceffg")), is(false));
        assertThat(isValid(getChars("abbcegjk")), is(false));
    }

    @Test
    public void testNextPassword() {
        assertThat(nextPassword("abcdefgh"), is("abcdffaa"));
        assertThat(nextPassword("ghijklmn"), is("ghjaabcc"));
    }

    @Test
    public void answerA() {
        assertThat(nextPassword("hxbxwxba"), is("hxbxxyzz"));
    }

    @Test
    public void answerB() {
        assertThat(nextPassword(nextPassword("hxbxwxba")), is("hxbxxyzz"));
    }
}
