package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day1TaxiTest {
    @Test
    public void distance1() throws Exception {
        assertThat(Day1Taxi.distance("R2, L3"), is(5));
    }
    @Test
    public void distance2() throws Exception {
        assertThat(Day1Taxi.distance("R2, R2, R2"), is(2));
    }
    @Test
    public void distance3() throws Exception {
        assertThat(Day1Taxi.distance("R5, L5, R5, R3"), is(12));
    }
    @Test
    public void revisit1() throws Exception {
        assertThat(Day1Taxi.firstRevisitDistance("R8, R4, R4, R8"), is(4));
    }
}
