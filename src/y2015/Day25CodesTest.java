package y2015;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day25CodesTest {

    @Test
    public void testNext() {
        assertThat(Day25Codes.next(20151125L), is(31916031L));
    }
    @Test
    public void test11() {
        assertThat(Day25Codes.code(1, 1), is(20151125L));
    }

    @Test
    public void test66() {
        assertThat(Day25Codes.code(6, 6), is(27995004L));
    }
}