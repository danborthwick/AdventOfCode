package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day16DragonCodeTest {

    @Test
    public void codes() {
        assertThat(Day16DragonCode.code("1"), is("100"));
        assertThat(Day16DragonCode.code("0"), is("001"));
        assertThat(Day16DragonCode.code("11111"), is("11111000000"));
        assertThat(Day16DragonCode.code("111100001010"), is("1111000010100101011110000"));
    }

    @Test
    public void checksums() {
        assertThat(Day16DragonCode.checksum("110010110100"), is("100"));
    }

    @Test
    public void fillDisk20() {
        assertThat(Day16DragonCode.fillDisk(20, "10000"), is("01100"));
    }

}