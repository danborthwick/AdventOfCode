package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day10BotsTest {
    @Test
    public void test() throws Exception {
        Day10Bots bots = new Day10Bots();
        bots.prepare(StringProvider.forArray(new String[]{
                "value 5 goes to bot 2",
                "bot 2 gives low to bot 1 and high to bot 0",
                "value 3 goes to bot 1",
                "bot 1 gives low to output 1 and high to bot 0",
                "bot 0 gives low to output 2 and high to output 0",
                "value 2 goes to bot 2",
        }));

        assertThat(bots.findComparer(2, 5), is(2));
        assertThat(bots.product(), is(30));
    }
}