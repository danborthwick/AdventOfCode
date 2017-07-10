package y2016;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day13MazeTest {

    @Test
    public void construction() {
        assertThat(makeMaze().rowString(2), is("#....##..."));
    }

    @Test
    public void path() {
        assertThat(makeMaze().pathLengthTo(7, 4), is(11));
    }

    @Test
    public void accessible() {
        assertThat(makeMaze().accessible(3), is(6));
    }

    private Day13Maze makeMaze() {
        return new Day13Maze(10, 10);
    }
}