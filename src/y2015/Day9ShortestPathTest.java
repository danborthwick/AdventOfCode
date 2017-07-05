package y2015;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day9ShortestPathTest {

    @Test
    public void test1() throws Exception
    {
        String[] input = new String[] {
                "London to Dublin = 464",
                "London to Belfast = 518",
                "Dublin to Belfast = 141"
        };

        Day9ShortestPath day9ShortestPath = new Day9ShortestPath(StringProvider.forArray(input));
        assertThat(day9ShortestPath.shortestPath(), is(605));
    }
}