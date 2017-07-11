package y2016;

import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

class Day15Discs {

    private static final Pattern PATTERN = Pattern.compile("Disc #\\d+ has (?<size>\\d+) positions; at time=0, it is at position (?<startPos>\\d+)\\.");
    final List<Disc> discs = new ArrayList<>();

    class Disc {

        final int size;
        final int startPos;
        Disc(int size, int startPos) {
            this.size = size;
            this.startPos = startPos;
        }

    }
    Day15Discs(StringProvider input) throws Exception {
        while (input.hasMore()) {
            Matcher matcher = PATTERN.matcher(input.next());
            if (!matcher.matches()) { throw new Exception("Didn't match"); }

            int size = parseInt(matcher.group("size"));
            int startPos = parseInt(matcher.group("startPos"));
            addDisc(size, startPos);
        }
    }

    void addDisc(int size, int startPos) {
        discs.add(new Disc(size, startPos));
    }

    int blockingDisc(int dropTime) {
        for (int distance = 0; distance < discs.size(); distance++) {
            int elapsed = dropTime + distance + 1;

            Disc disc = discs.get(distance);
            int position = (disc.startPos + elapsed) % disc.size;
            if (position != 0) {
                return distance;
            }
        }

        return -1;
    }

    int firstPass() {
        for (int dropTime = 0; ; dropTime++) {
            if (blockingDisc(dropTime) == -1) {
                return dropTime;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day15Input.txt");
        Day15Discs discs = new Day15Discs(input);
        System.out.println("Drop time: " + discs.firstPass());

        discs.addDisc(11, 0);
        System.out.println("Drop time: " + discs.firstPass());
    }
 }
