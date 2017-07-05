package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day3SquaresTest {
    @Test
    public void test1() throws Exception {
        assertThat(Day3Squares.countPossible(getInput()), is(1));
    }

    @Test
    public void testVertical() throws Exception {
        assertThat(Day3Squares.countPossibleVertical(getInput()), is(1));
    }


    private StringProvider getInput() {
        return StringProvider.forArray(new String[]{
                    "  5 10 25",
                    "  4 5 6",
                    "  8  100  9"
            });
    }
}