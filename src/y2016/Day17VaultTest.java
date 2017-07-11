package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day17VaultTest {

    @Test
    public void shortestFail() throws Exception {
        assertThat(new Day17Vault("hijkl").shortestPath(), is((String) null));
    }

    @Test
    public void shortest1() throws Exception {
        assertThat(new Day17Vault("ihgpwlah").shortestPath(), is("DDRRRD"));
    }

    @Test
    public void shortest2() throws Exception {
        assertThat(new Day17Vault("kglvqrro").shortestPath(), is("DDUDRLRRUDRD"));
    }

    @Test
    public void shortest3() throws Exception {
        assertThat(new Day17Vault("ulqzkmiv").shortestPath(), is("DRURDRUDDLLDLUURRDULRLDUUDDDRR"));
    }

    @Test
    public void longest1() throws Exception {
        assertThat(new Day17Vault("ihgpwlah").longestPath().length(), is(370));
    }

    @Test
    public void longest2() throws Exception {
        assertThat(new Day17Vault("kglvqrro").longestPath().length(), is(492));
    }

    @Test
    public void longest3() throws Exception {
        assertThat(new Day17Vault("ulqzkmiv").longestPath().length(), is(830));
    }
}