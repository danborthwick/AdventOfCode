package y2016;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day4ChecksumTest {
    @Test
    public void real1() {
        assertThat(Day4Checksum.isReal("aaaaa-bbb-z-y-x-123[abxyz]"), is(123));
    }

    @Test
    public void real2() {
        assertThat(Day4Checksum.isReal("a-b-c-d-e-f-g-h-987[abcde]"), is(987));
    }

    @Test
    public void real3() {
        assertThat(Day4Checksum.isReal("not-a-real-room-404[oarel]"), is(404));
    }

    @Test
    public void notReal() {
        assertThat(Day4Checksum.isReal("totally-real-room-200[decoy]"), is(0));
    }

    @Test
    public void decrypt() {
        assertThat(Day4Checksum.decrypt("qzmt-zixmtkozy-ivhz", 343), is("very encrypted name"));
    }
}