package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Histogram {
    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> histogram = makeHistogram(list);
        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : histogram.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public static <T> T leastCommon(List<T> list) {
        Map<T, Integer> histogram = makeHistogram(list);
        Map.Entry<T, Integer> min = null;

        for (Map.Entry<T, Integer> e : histogram.entrySet()) {
            if (min == null || e.getValue() < min.getValue())
                min = e;
        }

        return min.getKey();
    }

    private static <T> Map<T, Integer> makeHistogram(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }
        return map;
    }
}
