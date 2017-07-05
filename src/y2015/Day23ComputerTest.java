package y2015;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day23ComputerTest {

    @Test
    public void test1() throws Exception {
        String[] INPUT = new String[] {
                "inc a",
                "jio a, +2",
                "tpl a",
                "inc a"
        };

        StringProvider input = StringProvider.forArray(INPUT);
        Day23Computer computer = new Day23Computer(input);
        computer.run();
        assertThat(computer.getRegister('a'), is(2));
    }

}