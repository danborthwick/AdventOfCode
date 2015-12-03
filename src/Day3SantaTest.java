import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day3SantaTest {

    @Test
    public void test1() throws Exception {
        assertThat(Day3Santa.getRecipientCount(">"), is(2));
    }

    @Test
    public void test2() throws Exception {
        assertThat(Day3Santa.getRecipientCount("^>v<"), is(4));
    }

    @Test
    public void test3() throws Exception {
        assertThat(Day3Santa.getRecipientCount("^v^v^v^v^v"), is(2));
    }
    @Test
    public void roboTest1() throws Exception {
        assertThat(Day3RoboSanta.getRecipientCount("^v"), is(3));
    }

    @Test
    public void roboTest2() throws Exception {
        assertThat(Day3RoboSanta.getRecipientCount("^>v<"), is(3));
    }

    @Test
    public void roboTest3() throws Exception {
        assertThat(Day3RoboSanta.getRecipientCount("^v^v^v^v^v"), is(11));
    }

}