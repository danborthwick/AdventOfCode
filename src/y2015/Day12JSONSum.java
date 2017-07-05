package y2015;

import org.json.JSONArray;
import org.json.JSONObject;
import util.StringProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12JSONSum {
    public static int getTotal(StringProvider input) throws Exception {
        int result = 0;

        while (input.hasMore())   {
            result += lineTotal(input.next());
        }

        return result;
    }

    static Pattern pattern = Pattern.compile("(\\-?\\d+)");

    private static int lineTotal(String next) {
        int result = 0;

        Matcher matcher = pattern.matcher(next);
        while (matcher.find()) {
            result += Integer.valueOf(matcher.group());
        }

        return result;
    }

    private static int getTotalNoRed(StringProvider input) throws Exception
    {
        int result = 0;

        while (input.hasMore())   {
            result += lineTotalNoRed(input.next());
        }

        return result;
    }

    private static int lineTotalNoRed(String next) {
        JSONArray json = new JSONArray(next);
        return arrayTotal(json);
    }

    private static int jsonTotalNoRed(JSONObject json) {
        int result = 0;

        for (String key : json.keySet()) {

            Object childObject = json.get(key);

            if (childObject.equals("red"))
                return 0;

            result += valueOfObject(childObject);
        }

        return result;
    }

    private static int valueOfObject(Object childObject) {
        int result = 0;

        if (childObject instanceof JSONObject) {
            result = jsonTotalNoRed((JSONObject) childObject);
        }
        else if (childObject instanceof JSONArray) {
            result = arrayTotal((JSONArray) childObject);
        }
        else if (childObject instanceof Integer) {
            result = (Integer) childObject;
        }
        return result;
    }

    private static int arrayTotal(JSONArray array) {
        int result = 0;
        for (Object item : array) {
            result += valueOfObject(item);
        }

        return result;
    }


    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day12Input.txt");
        System.out.println("Total: " + getTotal(input));
        input = StringProvider.forFile("Day12Input.txt");
        System.out.println("Total no red: " + getTotalNoRed(input));
    }
}
