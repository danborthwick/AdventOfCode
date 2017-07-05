package y2016;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day7IP7Test {

    @Test
    public void tls1() {
        assertThat(Day7IP7.supportsTLS("abcd[bddb]xyyx"), is(false));
    }
    @Test
    public void tls2() {
        assertThat(Day7IP7.supportsTLS("abba[mnop]qrst"), is(true));
    }
    @Test
    public void tls3() {
        assertThat(Day7IP7.supportsTLS("aaaa[qwer]tyui"), is(false));
    }
    @Test
    public void tls4() {
        assertThat(Day7IP7.supportsTLS("ioxxoj[asdfgh]zxcvbn"), is(true));
    }

    @Test
    public void ssl1() {
        assertThat(Day7IP7.supportsSSL("aba[bab]xyz"), is(true));
    }
    @Test
    public void ssl2() {
        assertThat(Day7IP7.supportsSSL("xyx[xyx]xyx"), is(false));
    }
    @Test
    public void ssl3() {
        assertThat(Day7IP7.supportsSSL("aaa[kek]eke"), is(true));
    }
    @Test
    public void ssl4() {
        assertThat(Day7IP7.supportsSSL("zazbz[bzb]cdb"), is(true));
    }
}