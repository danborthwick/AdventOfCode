package y2016;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day8PixelsTest {

    private static final char[][] START_SCREEN = Day8Pixels.makeScreen(7, 3);

    private static final char[][] SCREEN_1 = makeScreen(new String[]{
            "###....",
            "###....",
            "......."
    });
    private static final char[][] SCREEN_2 = makeScreen(new String[]{
            "#.#....",
            "###....",
            ".#....."
    });
    private static final char[][] SCREEN_3 = makeScreen(new String[]{
            "....#.#",
            "###....",
            ".#....."
    });
    private static final char[][] SCREEN_4 = makeScreen(new String[]{
            ".#..#.#",
            "#.#....",
            ".#....."
    });

    @Test
    public void step1() throws Exception {
        assertThat(doStep("rect 3x2", START_SCREEN), is(SCREEN_1));
    }

    @Test
    public void step2() throws Exception {
        assertThat(doStep("rotate column x=1 by 1", SCREEN_1), is(SCREEN_2));
    }
    @Test
    public void step3() throws Exception {
        assertThat(doStep("rotate row y=0 by 4", SCREEN_2), is(SCREEN_3));
    }
    @Test
    public void step4() throws Exception {
        assertThat(doStep("rotate column x=1 by 1", SCREEN_3), is(SCREEN_4));
    }

    @Test
    public void count() throws Exception {
        assertThat(Day8Pixels.countPixels(SCREEN_3), is(6));
    }

    char[][] doStep(String instruction, final char[][] input) throws Exception {
        char[][] screen = input.clone();
        Day8Pixels.step(instruction, screen);
        return screen;
    }

    private static char[][] makeScreen(String[] strings) {
        char[][] screen = new char[strings.length][];
        for (int y=0; y < screen.length; y++) {
            screen[y] = strings[y].toCharArray();
        }
        return screen;
    }
}