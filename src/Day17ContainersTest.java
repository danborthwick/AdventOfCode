import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Day17ContainersTest {

    @Test
    public void testA() throws Exception {
        String[] input = {"20", "15", "10", "5", "5"};
        Day17Containers containers = new Day17Containers(StringProvider.forArray(input));
        assertThat(containers.numberOfCombinations(25), is(4));
    }
}