package y2016;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Day9DecompressionTest {
    @Test
    public void test1() {
        assertThat(Day9Decompression.decompressedLength("ADVENT"), is(6));
    }
    @Test
    public void test2() {
        assertThat(Day9Decompression.decompressedLength("A(1x5)BC"), is(7));
    }
    @Test
    public void test3() {
        assertThat(Day9Decompression.decompressedLength("(3x3)XYZ"), is(9));
    }
    @Test
    public void test4() {
        assertThat(Day9Decompression.decompressedLength("A(2x2)BCD(2x2)EFG"), is(11));
    }
    @Test
    public void test5() {
        assertThat(Day9Decompression.decompressedLength("(6x1)(1x3)A"), is(6));
    }
    @Test
    public void test6() {
        assertThat(Day9Decompression.decompressedLength("X(8x2)(3x3)ABCY"), is(18));
    }
}