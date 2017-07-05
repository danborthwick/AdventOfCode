package y2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Day5NaughtyOrNice {
    public static int countNiceA() throws Exception {
        StringProvider input = StringProvider.forFile("Day5Input.txt");
        int niceStrings = 0;

        while (input.hasMore())   {
            if (isNiceA(input.next())) {
                niceStrings++;
            }
        }

        return niceStrings;
    }

    static Pattern vowels = Pattern.compile("[aeiou][^aeiou]*[aeiou][^aeiou]*[aeiou]");
    static Pattern consecutive = Pattern.compile("([a-zA-Z])\\1");
    static Pattern naughty = Pattern.compile("(ab)|(cd)|(pq)|(xy)");

    public static boolean isNiceA(String s) {
        boolean result = vowels.matcher(s).find();
        result &= consecutive.matcher(s).find();
        result &= !naughty.matcher(s).find();

        return result;
    }

    public static int countNiceB() throws Exception {
        FileInputStream fstream = new FileInputStream("Day5Input.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        int niceStrings = 0;

        while ((strLine = br.readLine()) != null)   {
            if (isNiceB(strLine)) {
                niceStrings++;
            }
        }

        return niceStrings;
    }

    static Pattern repeatPair = Pattern.compile("([a-z][a-z]).*\\1");
    static Pattern repeatLetter = Pattern.compile("([a-z]).\\1");

    public static boolean isNiceB(String s) {
        boolean result = repeatPair.matcher(s).find();
        result &= repeatLetter.matcher(s).find();
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("NiceA: " + countNiceA());
        System.out.println("NiceB: " + countNiceB());
    }
}
