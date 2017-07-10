package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day12RegistersTest {

    @Test
    public void test() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "cpy 41 a",
                "inc a",
                "inc a",
                "dec a",
                "jnz a 2",
                "dec a"
        });

        Day12Registers registers = new Day12Registers(input);
        assertThat(registers.execute(), is(42));
    }
}