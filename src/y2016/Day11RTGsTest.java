package y2016;

import org.junit.Test;
import util.StringProvider;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day11RTGsTest {

    @Test
    public void test() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.",
                "The second floor contains a hydrogen generator.",
                "The third floor contains a lithium generator.",
                "The fourth floor contains nothing relevant."
        });

        Day11RTGs rtgs = new Day11RTGs(input);
        List<Day11RTGs.Pair> choices = rtgs.getChoices();

        assertThat(rtgs.elementCount, is(2));
        assertThat(rtgs.stateCount, is((int) Math.pow(4, 5)));

        rtgs.traceState(9, "Initial");
        assertThat(rtgs.initialState, is(1 << 0| 2 << 2));
        rtgs.traceState(281, "281");

        assertThat(rtgs.getNextStates(9, choices), contains(281));
        assertThat(rtgs.getNextStates(281, choices), not(empty()));

        assertThat(rtgs.minSteps(), is(11));
    }

    @Test
    public void testRealInputPerformance() throws Exception {
        StringProvider input = StringProvider.forFile("2016Day11Input.txt");
        Day11RTGs rtgs = new Day11RTGs(input);
        assertThat(rtgs.elementCount, is(5));

        rtgs.minSteps();
    }
}