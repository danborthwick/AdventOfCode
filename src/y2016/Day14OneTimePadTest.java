package y2016;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day14OneTimePadTest {

    @Test
    public void test() throws Exception {
        assertThat(new Day14OneTimePad("abc", 0).indexOfKey(64), is(22728));
    }

    @Test
    public void stretched() throws Exception {
        assertThat(new Day14OneTimePad("abc", 2016).indexOfKey(64), is(22551));
    }

    @Ignore @Test
    public void naiive() throws Exception {
        assertThat(new Day14OneTimePad("abc", 0).indexOfKeyNaiive(64), is(22728));
    }

    @Ignore @Test
    public void compareKeys() throws Exception {
        Day14OneTimePad pad = new Day14OneTimePad("abc", 0);
        List<Integer> keys = pad.getKeys(64);
        List<Integer> keysNaiive = pad.getKeysNaiive(64);

        assertThat(keys, is(keysNaiive));
    }
}