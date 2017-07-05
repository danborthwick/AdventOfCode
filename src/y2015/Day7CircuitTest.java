package y2015;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

public class Day7CircuitTest {

    @Test
    public void testA1() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "123 -> x",
                "456 -> y",
                "x AND y -> d",
                "x OR y -> e",
                "x LSHIFT 2 -> f",
                "y RSHIFT 2 -> g",
                "NOT x -> h",
                "NOT y -> i",
        });

        String[] expected = new String[]{
                "d: 72",
                "e: 507",
                "f: 492",
                "g: 114",
                "h: 65412",
                "i: 65079",
                "x: 123",
                "y: 456",
        };

        Day7Circuit.Circuit circuit = new Day7Circuit.Circuit(input);
        assertThat(circuit.allWires(), arrayContainingInAnyOrder(expected));
        assertThat(circuit.wire("f"), is(492));
    }

}