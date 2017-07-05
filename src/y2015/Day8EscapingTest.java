package y2015;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day8EscapingTest {

    private static final String[] inputStrings = new String[]{
            "\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\""
    };;

    @Test
    public void testCodeLength() {
        Object[] actual = Arrays.stream(inputStrings).map(Day8Escaping::codeLength).toArray();
        assertThat(actual, is(new Integer[] { 2, 5, 10, 6}));
    }

    @Test
    public void testMemoryLength() {
        Object[] actual = Arrays.stream(inputStrings).map(Day8Escaping::memoryLength).toArray();
        assertThat(actual, is(new Integer[] { 0, 3, 7, 1}));
    }

    @Test
    public void testEncodedLength() {
        Object[] actual = Arrays.stream(inputStrings).map(Day8Escaping::encodedLength).toArray();
        assertThat(actual, is(new Integer[] { 6, 9, 16, 11}));
    }

    @Test
    public void testCodeMemoryDifference() throws Exception {
        StringProvider input = StringProvider.forArray(inputStrings);

        assertThat(Day8Escaping.codeMemoryDifference(input), is(12));
    }

    @Test
    public void testEncodedCodeDifference() throws Exception
    {
        StringProvider input = StringProvider.forArray(inputStrings);
        assertThat(Day8Escaping.encodedCodeDifference(input), is(19));
    }
}