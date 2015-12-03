import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day2Ribbon {

    public static long getRibbon() throws IOException {
        int totalArea = 0;

        FileInputStream fstream = new FileInputStream("Day2Input.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

//Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            List<Integer> dimensions = new ArrayList(3);
            for (String s : strLine.split("x")) {
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

    public static void main(String[] args) throws IOException {
        System.out.println("Ribbon " + getRibbon());
    }
}
