import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day15Cookies {

    public static final int TOTAL_TEASPOONS = 100;
    private static final int PROPERTY_COUNT = 4;

    static class Ingredient {
        public final int[] properties;
        public final int calories;

        private static Pattern pattern = Pattern.compile("(?:\\w+): capacity (\\-?\\d+), durability (\\-?\\d+), " +
                "flavor (\\-?\\d+), texture (\\-?\\d+), calories (?<calories>\\-?\\d+)");

        public Ingredient(String line) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();

            properties = new int[PROPERTY_COUNT];
            for (int p = 0; p < PROPERTY_COUNT; p++) {
                properties[p] = Integer.valueOf(matcher.group(p + 1));
            }

            calories = Integer.valueOf(matcher.group("calories"));
        }
    }

    private static long bestTotalScore(StringProvider stringProvider, int requiredCalories) throws Exception {
        List<Ingredient> ingredients = new ArrayList();
        while (stringProvider.hasMore()) {
            ingredients.add(new Ingredient(stringProvider.next()));
        }

        int[] amounts = new int[ingredients.size()];
        return bestScore(ingredients, amounts, 0, TOTAL_TEASPOONS, requiredCalories);
    }

    private static long score(List<Ingredient> ingredients, int[] amounts) {
        long result = 1;

        for (int property = 0; property < PROPERTY_COUNT; property++) {
            long propertyTotal = 0;

            for (int ingredient = 0; ingredient < ingredients.size(); ingredient++) {
                propertyTotal += ingredients.get(ingredient).properties[property] * amounts[ingredient];
            }

            propertyTotal = Math.max(propertyTotal, 0);
            result *= propertyTotal;
        }

        return result;
    }

    private static long bestScore(List<Ingredient> ingredients, int[] amounts, int k, int teaspoonsRemaining, int requiredCalories) {
        amounts[k] = teaspoonsRemaining;

        if (k == amounts.length - 1) {
            if ((requiredCalories < 0) || (calories(ingredients, amounts) == requiredCalories))
                return score(ingredients, amounts);
            else
                return 0;
        }
        else {
           long bestScore = 0;

            while (amounts[k] >= 0) {
                bestScore = Math.max(bestScore, bestScore(ingredients, amounts, k + 1, teaspoonsRemaining - amounts[k], requiredCalories));
                amounts[k]--;
            }

            return bestScore;
        }
    }

    private static int calories(List<Ingredient> ingredients, int[] amounts) {
        int calories = 0;
        for (int i=0; i < ingredients.size(); i++) {
            calories += ingredients.get(i).calories * amounts[i];
        }
        return calories;
    }

    public static final String[] TEST_INPUT = new String[]{
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
            "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
    };

    @Test
    public void testA() throws Exception
    {
        assertThat(bestTotalScore(StringProvider.forArray(TEST_INPUT), -1), is(62842880L));
    }

    @Test
    public void testB() throws Exception
    {
        assertThat(bestTotalScore(StringProvider.forArray(TEST_INPUT), 500), is(57600000L));
    }

    public static void main(String[] args) throws Exception
    {
        StringProvider input = StringProvider.forFile("Day15Input.txt");
        System.out.println("Result A: " + bestTotalScore(input, -1));

        input = StringProvider.forFile("Day15Input.txt");
        System.out.println("Result B: " + bestTotalScore(input, 500));
    }

}
