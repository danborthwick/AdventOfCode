package y2015;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day2Test {

    @Test
    public void area1() {
        assertThat(Day2Boxes.paperArea(Arrays.asList(2, 3, 4)), is(58));
    }

    @Test
    public void area2() {
        assertThat(Day2Boxes.paperArea(Arrays.asList(1, 1, 10)), is(43));
    }

    @Test
    public void ribbon1() {
        assertThat(Day2Ribbon.ribbon(Arrays.asList(2, 3, 4)), is(34));
    }

    @Test
    public void ribbon2() {
        assertThat(Day2Ribbon.ribbon(Arrays.asList(1, 1, 10)), is(14));
    }

}