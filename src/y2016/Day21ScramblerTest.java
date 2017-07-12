package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day21ScramblerTest {
    @Test
    public void scramble() throws Exception {
        Day21Scrambler scrambler = new Day21Scrambler("abcde");

        assertThat(scrambler.stepAndGetState("swap position 4 with position 0"), is("ebcda"));
        assertThat(scrambler.stepAndGetState("swap letter d with letter b"), is("edcba"));
        assertThat(scrambler.stepAndGetState("reverse positions 0 through 4"), is("abcde"));
        assertThat(scrambler.stepAndGetState("rotate left 1 step"), is("bcdea"));
        assertThat(scrambler.stepAndGetState("move position 1 to position 4"), is("bdeac"));
        assertThat(scrambler.stepAndGetState("move position 3 to position 0"), is("abdec"));
        assertThat(scrambler.stepAndGetState("rotate based on position of letter b"), is("ecabd"));
        assertThat(scrambler.stepAndGetState("rotate based on position of letter d"), is("decab"));
    }

    @Test
    public void unscramble() throws Exception {
        Day21Scrambler scrambler = new Day21Scrambler("abcde");

        StringProvider instructions = StringProvider.forArray(new String[]{
                "swap position 4 with position 0",
                "swap letter d with letter b",
                "reverse positions 0 through 4",
                "rotate left 1 step",
                "move position 1 to position 4",
                "move position 3 to position 0",
                "rotate based on position of letter b",
                "rotate based on position of letter d"
        });

        assertThat(scrambler.unscramble(instructions, "decab"), is("abcde"));

    }

    @Test
    public void rotateRight() throws Exception {
        Day21Scrambler scrambler = new Day21Scrambler("abcde");
        assertThat(scrambler.stepAndGetState("rotate right 2 steps"), is("deabc"));
    }

}