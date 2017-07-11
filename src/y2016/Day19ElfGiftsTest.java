package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day19ElfGiftsTest {

    @Test
    public void fiveElvesSimple() {
        assertThat(Day19ElfGifts.winner(5), is(3));
    }

    @Test
    public void fiveElvesCenter() {
        assertThat(Day19ElfGifts.winnerCenter(5), is(2));
    }

}