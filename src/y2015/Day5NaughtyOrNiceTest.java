package y2015;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class Day5NaughtyOrNiceTest {

    @Test
    public void testisNiceA1() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceA("ugknbfddgicrmopn"), is(true));
    }

    @Test
    public void testisNiceA2() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceA("aaa"), is(true));
    }

    @Test
    public void testisNiceA3() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceA("jchzalrnumimnmhp"), is(false));
    }

    @Test
    public void testisNiceA4() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceA("haegwjzuvuyypxyu"), is(false));
    }

    @Test
    public void testisNiceA5() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceA("dvszwmarrgswjxmb"), is(false));
    }

    @Test
    public void testisNiceB1() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceB("qjhvhtzxzqqjkmpb"), is(true));
    }

    @Test
    public void testisNiceB2() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceB("xxyxx"), is(true));
    }

    @Test
    public void testisNiceB3() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceB("uurcxstgmygtbstg"), is(false));
    }

    @Test
    public void testisNiceB4() throws Exception {
        assertThat(Day5NaughtyOrNice.isNiceB("ieodomkazucvgmuy"), is(false));
    }


}