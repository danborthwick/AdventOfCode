package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day18TrapsTest {

    @Test
    public void rowCreation() {
        assertThat(Day18Traps.nextRow("..^^."), is(".^^^^"));
        assertThat(Day18Traps.nextRow(".^^^^"), is("^^..^"));
    }

    @Test
    public void countSafe() {
        Day18Traps traps = new Day18Traps(".^^.^.^^^^", 10);
        assertThat(traps.safeCount(), is(38));
    }
}