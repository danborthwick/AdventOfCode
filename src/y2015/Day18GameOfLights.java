package y2015;

import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day18GameOfLights {

    public static final char ON = '#';

    List<List<Boolean>> current = new ArrayList<>();
    List<List<Boolean>> other = new ArrayList<>();
    private boolean cornerLightsStuck;

    public Day18GameOfLights(StringProvider input, boolean cornerLightsStuck) throws Exception {
        this.cornerLightsStuck = cornerLightsStuck;
        while (input.hasMore()) {
            String lineString = input.next();
            List<Boolean> lineList = lineString.chars().mapToObj(i -> i == ON).collect(Collectors.toList());
            current.add(lineList);
            other.add(new ArrayList<>(lineList));
        }

        updateCornerLights();
    }

    public void runSteps(int stepCount) {
        while (stepCount-- > 0) {
            step();
        }
    }

    private void step() {
        for (int y = 0; y < height(); y++) {
            for (int x=0; x < width(); x++) {
                int neighboursOn = neighbourCount(x, y);
                boolean currentState = getCurrent(x, y);
                boolean newState;

                if (currentState) {
                    newState = (neighboursOn == 2) || (neighboursOn == 3);
                }
                else {
                    newState = neighboursOn == 3;
                }

                setOther(x, y, newState);
            }
        }

        swapBuffers();
        updateCornerLights();
    }

    private void updateCornerLights() {
        if (cornerLightsStuck) {
            setCurrent(0, 0, true);
            setCurrent(0, width() - 1, true);
            setCurrent(height() - 1, 0, true);
            setCurrent(height() - 1, width() - 1, true);
        }
    }

    private int width() {
        return current.get(0).size();
    }

    private int height() {
        return current.size();
    }

    private int neighbourCount(int x, int y) {
        int count = 0;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {

                if ((dx == 0) && (dy == 0))
                    continue;

                if (getCurrent(x + dx, y + dy))
                    count++;
            }

        }

        return count;
    }

    private boolean validCoordinate(int x, int y) {
        return (x >= 0) && (x < current.get(0).size()) && (y >= 0) && (y < current.size());
    }

    private void setCurrent(int x, int y, boolean newState) {
        current.get(y).set(x, newState);
    }

    private void setOther(int x, int y, boolean newState) {
        other.get(y).set(x, newState);
    }

    private boolean getCurrent(int x, int y) {
        return validCoordinate(x, y) && current.get(y).get(x);
    }

    private void swapBuffers() {
        List<List<Boolean>> temp = current;
        current = other;
        other = temp;
    }

    public int lightsOn() {
        return (int) current.stream().flatMap(list -> list.stream()).filter(b -> b == true).count();
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day18Input.txt");
        Day18GameOfLights lights = new Day18GameOfLights(input, false);
        lights.runSteps(100);
        System.out.println("Lights after 100 steps: " + lights.lightsOn());

        input = StringProvider.forFile("Day18Input.txt");
        lights = new Day18GameOfLights(input, true);
        lights.runSteps(100);
        System.out.println("Lights after 100 steps with corners stuck: " + lights.lightsOn());
    }
}
