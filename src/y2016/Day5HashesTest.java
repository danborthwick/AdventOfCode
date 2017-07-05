package y2016;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day5HashesTest {

    @Test
    public void testAbc() throws NoSuchAlgorithmException {
        assertThat(Day5Hashes.password("abc"), is("18f47a30"));
    }

    @Test
    public void testUnordered() throws Exception {
        assertThat(Day5Hashes.passwordUnordered("abc"), is("05ace8e3"));
    }
}