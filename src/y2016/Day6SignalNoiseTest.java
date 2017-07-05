package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day6SignalNoiseTest {

    public static StringProvider makeInput() {
        return StringProvider.forArray(new String[]{
                "eedadn",
                "drvtee",
                "eandsr",
                "raavrd",
                "atevrs",
                "tsrnev",
                "sdttsa",
                "rasrtv",
                "nssdts",
                "ntnada",
                "svetve",
                "tesnvt",
                "vntsnd",
                "vrdear",
                "dvrsen",
                "enarar"
        });
    }

    @Test
    public void testMost() throws Exception {
        assertThat(Day6SignalNoise.decode(makeInput()), is("easter"));
    }

    @Test
    public void testLeast() throws Exception {
        assertThat(Day6SignalNoise.decodeLeast(makeInput()), is("advent"));
    }

}