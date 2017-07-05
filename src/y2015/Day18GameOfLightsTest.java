package y2015;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day18GameOfLightsTest {

    private static final String[] TEST_INPUT = new String[]{
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."
    };

    @Test
    public void testNormalCorners() throws Exception
    {
        StringProvider input = StringProvider.forArray(TEST_INPUT);
        Day18GameOfLights lights = new Day18GameOfLights(input, false);
        lights.runSteps(4);
        assertThat(lights.lightsOn(), is(4));
    }

    @Test
    public void testStuckCorners() throws Exception
    {
        StringProvider input = StringProvider.forArray(TEST_INPUT);
        Day18GameOfLights lights = new Day18GameOfLights(input, true);
        lights.runSteps(5);
        assertThat(lights.lightsOn(), is(17));
    }

}