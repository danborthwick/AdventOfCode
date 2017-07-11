package y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day17VaultTest {

    @Test
    public void failCase() throws Exception {
        assertThat(new Day17Vault("hijkl").shortestPath(), is((String) null));
    }

    @Test
    public void passCase1() throws Exception {
        assertThat(new Day17Vault("ihgpwlah").shortestPath(), is("DDRRRD"));
    }

    @Test
    public void passCase2() throws Exception {
        assertThat(new Day17Vault("kglvqrro").shortestPath(), is("DDUDRLRRUDRD"));
    }

    @Test
    public void passCase3() throws Exception {
        assertThat(new Day17Vault("ulqzkmiv").shortestPath(), is("DRURDRUDDLLDLUURRDULRLDUUDDDRR"));
    }

}