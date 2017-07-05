package y2015;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day20FactorDeliveriesTest {

    @Test
    public void test1() {
        assertThat(Day20FactorDeliveries.minHouseNumberReceivingAtLeast(140), is(8));
    }

}