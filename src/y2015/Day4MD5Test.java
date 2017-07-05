package y2015;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day4MD5Test {

    @Test
    public void test1() throws Exception {
        assertThat(Day4MD5.getLowestNumberWithZeroHash("abcdef", 5), is(609043));
    }

    @Test
    public void test2() throws Exception {
        assertThat(Day4MD5.getLowestNumberWithZeroHash("pqrstuv", 5), is(1048970));
    }

    @Test
    public void testReal() throws Exception {
        assertThat(Day4MD5.getLowestNumberWithZeroHash("iwrupvqb", 5), is(346386));
    }

    @Test
    public void testRealB() throws Exception {
        assertThat(Day4MD5.getLowestNumberWithZeroHash("iwrupvqb", 6), is(9958218));
    }

}