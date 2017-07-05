package y2015;

import org.junit.Test;
import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day14Reindeer {

    public static final String[] TEST_INPUT = new String[]{
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
            "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
    };

    public static int distanceOfWinner(StringProvider input, int duration) throws Exception {
        int maxDistance = 0;

        while (input.hasMore()) {
            maxDistance = Math.max(maxDistance, distanceOfReindeer(input.next(), duration));
        }

        return maxDistance;
    }

    static final Pattern pattern = Pattern.compile("\\w+ can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.");

    private static int distanceOfReindeer(String line, int duration) {

        Reindeer reindeer = Reindeer.fromLine(line);

        for (int elapsed = 0; elapsed < duration; elapsed++) {
            reindeer.update(elapsed);
        }

        return reindeer.distance;
    }

    static class Reindeer {

        public final int speed;
        public final int flightTime;
        public final int restTime;

        public int distance = 0;
        public int score = 0;

        Reindeer(int speed, int flightTime, int restTime) {
            this.speed = speed;
            this.flightTime = flightTime;
            this.restTime = restTime;
        }

        public static Reindeer fromLine(String line) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();

            return new Reindeer(
                    Integer.valueOf(matcher.group(1)),
                    Integer.valueOf(matcher.group(2)),
                    Integer.valueOf(matcher.group(3)));
        }

        public void update(int elapsed) {
            int phase = elapsed % (flightTime + restTime);
            if (phase < flightTime) {
                distance += speed;
            }
        }
    }

    private static int scoreOfWinner(StringProvider input, int duration) throws Exception {

        List<Reindeer> allReindeer = new ArrayList();
        while (input.hasMore()) {
            allReindeer.add(Reindeer.fromLine(input.next()));
        }

        for (int elapsed = 0; elapsed < duration; elapsed++) {

            int maxDistance = -1;

            for (Reindeer reindeer : allReindeer) {
                reindeer.update(elapsed);
                maxDistance = Math.max(maxDistance, reindeer.distance);
            }

            for (Reindeer reindeer : allReindeer) {
                if (reindeer.distance == maxDistance) {
                    reindeer.score++;
                }
            }
        }

        int maxScore = -1;
        for (Reindeer reindeer : allReindeer) {
            maxScore = Math.max(maxScore, reindeer.score);
        }
        return maxScore;
    }

    @Test
    public void testDistance() throws Exception {
        StringProvider input = StringProvider.forArray(TEST_INPUT);
        assertThat(distanceOfWinner(input, 1000), is(1120));
    }

    @Test
    public void testScore() throws Exception {
        StringProvider input = StringProvider.forArray(TEST_INPUT);
        assertThat(scoreOfWinner(input, 1000), is(689));
    }

    public static void main(String[] args) throws Exception
    {
        StringProvider input = StringProvider.forFile("Day14Input.txt");
        System.out.println("Distance: " + distanceOfWinner(input, 2503));

        input = StringProvider.forFile("Day14Input.txt");
        System.out.println("Score: " + scoreOfWinner(input, 2503));
    }
}
