package y2016;

import util.StringProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class Day20Firewall {

    public static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    final Range blacklistRoot;
    private long maxValue;

    static class Range {

        long from;  // inclusive
        long to;    // exclusive

        Range prev;
        Range next;

        Range(long from, long to) {
            this.from = from;
            this.to = to;
        }

        void insertAfter(Range inserted) {
            inserted.next = this.next;
            inserted.prev = this;

            this.next = inserted;
            if (inserted.next != null) {
                inserted.next.prev = inserted;
            }
        }

        Range remove() {
            prev.next = this.next;
            if (this.next != null) {
                this.next.prev = this.prev;
            }
            return this.next;
        }
    }

    public Day20Firewall(long maxValue, StringProvider input) throws Exception {
        this.maxValue = maxValue;
        blacklistRoot = new Range(Long.MIN_VALUE, 0L);
        blacklistRoot.insertAfter(new Range(maxValue + 1L, Long.MAX_VALUE));

        while (input.hasMore()) {
            Matcher matcher = PATTERN.matcher(input.next());
            if (!matcher.matches()) { throw new Exception("Parse error"); }

            Range newRange = new Range(parseLong(matcher.group(1)), parseLong(matcher.group(2)) + 1L);

            Range before = blacklistRoot;
            Range after = before.next;

            while ((after != null) && (after.from < newRange.from)) {
                before = after;
                after = after.next;
            }

            // Now before.from < newRange.from < after.from

            if (newRange.from <= before.to) {
                //c0, c2 discard/merge
                before.to = Math.max(before.to, newRange.to);
            }
            else if (newRange.to >= after.from) {
                before.insertAfter(newRange);
                while ((after != null) && (newRange.to >= after.from)) {
                    newRange.to = Math.max(newRange.to, after.to);
                    after = after.remove();
                }
            }
            else {
                before.insertAfter(newRange);
                before = newRange;
            }

            while ((after != null) && (before.to >= after.from)) {
                before.to = Math.max(before.to, after.to);
                after = after.remove();
            }

            //TEMP
            validate();
        }
    }

    void validate() throws Exception {
        Range prev = null;
        for (Range range = blacklistRoot; range != null; range = range.next) {
            if (prev != null) {
                if ((prev.to >= range.from)
                || (range.from >= range.to)) {
                    throw new Exception("Invalid list!");
                }
            }
            prev = range;
        }

    }

    long minAllowed() {
        return blacklistRoot.to;
    }

    long countAllowed() {
        long count = 0L;
        Range prev = null;

        for (Range range = blacklistRoot; range != null; range = range.next) {
            if (prev != null) {
                count += range.from - prev.to;
            }
            prev = range;
        }

        return count;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day20Input.txt");
        Day20Firewall firewall = new Day20Firewall(4294967295L, input);
        System.out.println("Min allowed: " + firewall.minAllowed());
        System.out.println("Count allowed: " + firewall.countAllowed());
    }
}
