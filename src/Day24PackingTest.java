import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day24PackingTest {

    @Test
    public void test1() throws Exception {
        final String[] input = new String[] {
                "1", "2", "3", "4", "5", "7", "8", "9", "10", "11"
        };

        Day24Packing packing = new Day24Packing(StringProvider.forArray(input));
        assertThat(packing.quantumEntanglement(), is(99));
    }


}