package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day24RobotTest {

    @Test
    public void readMap() throws Exception {
        Day24Robot robot = makeExample();
        assertThat(robot.isWall(2, 2), is(true));
        assertThat(robot.isWall(3, 1), is(false));
        assertThat(robot.positionOf(2), is(new Day24Robot.Position(9, 1)));
    }

    @Test
    public void pairDistance() throws Exception {
        Day24Robot robot = makeExample();
        assertThat(robot.distanceBetween(1, 4), is(4));
        assertThat(robot.distanceBetween(2, 3), is(2));
    }

    @Test
    public void shortestDistance() throws Exception {
        assertThat(makeExample().shortestDistance(), is(14));
    }

    Day24Robot makeExample() throws Exception {
        StringProvider input = StringProvider.forArray(new String[]{
                "###########",
                "#0.1.....2#",
                "#.#######.#",
                "#4.......3#",
                "###########"
        });

        return new Day24Robot(input);
    }
}