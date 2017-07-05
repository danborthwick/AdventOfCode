package y2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day2Boxes {

    public static long getWrappingPaper() throws Exception {
        int totalArea = 0;

        StringProvider input = StringProvider.forFile("Day2Input.txt");
        while (input.hasMore())   {
            List<Integer> dimensions = new ArrayList(3);
            for (String s : input.next().split("x")) {
                dimensions.add(Integer.parseInt(s));
            }
            totalArea += paperArea(dimensions);
        }

        return totalArea;
    }

    public static int paperArea(List<Integer> dimensions) {
        int result = 0;
        int minSide = Integer.MAX_VALUE;
        for (int i=0; i < 3; i++) {
            int area = dimensions.get(i) * dimensions.get((i + 1) % 3);
            result += 2 * area;
            minSide = Math.min(minSide, area);
        }
        result += minSide;
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Required " + getWrappingPaper());
    }
}
