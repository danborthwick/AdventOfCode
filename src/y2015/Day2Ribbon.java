package y2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day2Ribbon {

    public static long getRibbon() throws Exception {
        int totalArea = 0;

        StringProvider input = StringProvider.forFile("Day2Input.txt");
        while (input.hasMore()) {
            List<Integer> dimensions = new ArrayList(3);
            for (String s : input.next().split("x")) {
                dimensions.add(Integer.parseInt(s));
            }
            totalArea += ribbon(dimensions);
        }

        return totalArea;
    }

    public static int ribbon(List<Integer> dimensions) {
        int minSide = Integer.MAX_VALUE;
        int volume = 1;
        for (int i=0; i < 3; i++) {
            int perimeter= (dimensions.get(i) + dimensions.get((i + 1) % 3)) * 2;
            minSide = Math.min(minSide, perimeter);
            volume *= dimensions.get(i);
        }
        int result = minSide;
        result += volume;
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Ribbon " + getRibbon());
    }
}
