package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day20FirewallTest {

    @Test
    public void minAllowed() throws Exception {
        assertThat(makeFirewall().minAllowed(), is(3L));
    }

    @Test
    public void countAllowed() throws Exception {
        assertThat(makeFirewall().countAllowed(), is(2L));
    }

    @Test
    public void countAllowed2() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "4-6",
                "0-2",
                "4-7"
        });

        assertThat(new Day20Firewall(10, input).countAllowed(), is(4L));
    }


    Day20Firewall makeFirewall() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "5-8",
                "0-2",
                "4-7"
        });
        return new Day20Firewall(9, input);
    }
}