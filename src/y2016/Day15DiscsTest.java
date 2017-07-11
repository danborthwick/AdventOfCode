package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day15DiscsTest {

    @Test
    public void example1() throws Exception {
        assertThat(makeDiscs().blockingDisc(0), is(1));
    }

    @Test
    public void firstPass() throws Exception {
        assertThat(makeDiscs().firstPass(), is(5));
    }

    private Day15Discs makeDiscs() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "Disc #1 has 5 positions; at time=0, it is at position 4.",
                "Disc #2 has 2 positions; at time=0, it is at position 1."
        });
        return new Day15Discs(input);
    }
}