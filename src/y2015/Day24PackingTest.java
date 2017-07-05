package y2015;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day24PackingTest {

    public static final String[] INPUT = new String[]{
            "1", "2", "3", "4", "5", "7", "8", "9", "10", "11"
    };

    @Test
    public void testThreeCompartments() throws Exception {
        Day24Packing packing = new Day24Packing(StringProvider.forArray(INPUT));
        assertThat(packing.quantumEntanglement(3), is(99L));
    }

    @Test
    public void testFourCompartments() throws Exception {
        Day24Packing packing = new Day24Packing(StringProvider.forArray(INPUT));
        assertThat(packing.quantumEntanglement(4), is(44L));
    }


}