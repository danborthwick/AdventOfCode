package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ChooserTest {


    List<Character> abcList = Arrays.asList('a', 'b', 'c');

    @Test
    public void testChoose1() {

        List<List<Character>> actual = new ArrayList<>();

        new Chooser<>(abcList).choose(1, l -> { actual.add(new ArrayList<>(l)); return null; });

        assertThat(actual, containsInAnyOrder(Arrays.asList('a'), Arrays.asList('b'), Arrays.asList('c')));
    }

    @Test
    public void testChoose2() {

        List<List<Character>> actual = new ArrayList<>();

        new Chooser<>(abcList).choose(2, l -> { actual.add(new ArrayList<>(l)); return null; });

        assertThat(actual, containsInAnyOrder(Arrays.asList('a', 'b'), Arrays.asList('b', 'c'), Arrays.asList('a', 'c')));
    }
}