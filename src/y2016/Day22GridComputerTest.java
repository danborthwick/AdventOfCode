package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day22GridComputerTest {

    @Test
    public void minimumMoves() throws Exception {
        assertThat(makeComputer().minimumMoves(), is(7));
    }

    @Test
    public void gridConstruction() throws Exception {
        assertThat(makeComputer().node(0, 2).size, is(32));
        assertThat(makeComputer().node(2, 1).used, is(8));
    }

    Day22GridComputer makeComputer() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "Filesystem            Size  Used  Avail  Use%",
                "/dev/grid/node-x0-y0   10T    8T     2T   80%",
                "/dev/grid/node-x0-y1   11T    6T     5T   54%",
                "/dev/grid/node-x0-y2   32T   28T     4T   87%",
                "/dev/grid/node-x1-y0    9T    7T     2T   77%",
                "/dev/grid/node-x1-y1    8T    0T     8T    0%",
                "/dev/grid/node-x1-y2   11T    7T     4T   63%",
                "/dev/grid/node-x2-y0   10T    6T     4T   60%",
                "/dev/grid/node-x2-y1    9T    8T     1T   88%",
                "/dev/grid/node-x2-y2    9T    6T     3T   66%"
        });

        return new Day22GridComputer(input);
    }
}