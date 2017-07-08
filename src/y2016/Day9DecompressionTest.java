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

    @Test
    public void test21() {
        assertThat(Day9Decompression.decompressedLength2("(3x3)XYZ"), is(9L));
    }
    @Test
    public void test22() {
        assertThat(Day9Decompression.decompressedLength2("X(8x2)(3x3)ABCY"), is(20L));
    }
    @Test
    public void test23() {
        assertThat(Day9Decompression.decompressedLength2("(27x12)(20x12)(13x14)(7x10)(1x12)A"), is(241920L));
    }
    @Test
    public void test24() {
        assertThat(Day9Decompression.decompressedLength2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"), is(445L));
    }
}