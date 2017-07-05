package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day2NumPadTest {

    @Test
    public void code1() throws Exception {
        StringProvider input = getInput();
        assertThat(Day2NumPad.code(input), is("1985"));
    }

    @Test
    public void code2() throws Exception {
        StringProvider input = getInput();
        assertThat(Day2NumPad.code2(input), is("5DB3"));
    }

    private StringProvider getInput() {
        return StringProvider.forArray(new String[] {
                    "ULL",
                    "RRDDD",
                    "LURDL",
                    "UUUUD"});
    }
}