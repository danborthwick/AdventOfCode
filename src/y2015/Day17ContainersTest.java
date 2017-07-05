package y2015;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Day17ContainersTest {

    static String[] input = {"20", "15", "10", "5", "5"};

    @Test
    public void testA() throws Exception {
        Day17Containers containers = new Day17Containers(StringProvider.forArray(input));
        assertThat(containers.numberOfCombinations(25), is(4));
    }

    @Test
    public void testPermutationsOfMinimumContainersUsed() throws Exception {
        Day17Containers containers = new Day17Containers(StringProvider.forArray(input));
        containers.numberOfCombinations(25);
        assertThat(containers.permutationsOfMinimumContainersUsed(), is(3));
    }
}