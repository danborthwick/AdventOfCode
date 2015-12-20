import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day19MedicineTest {

    static final String[] TEST_INPUT_A = new String[]{
            "H => HO",
            "H => OH",
            "O => HH",
            "HOH"
    };
    private static final String[] TEST_INPUT_B = new String[]{
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
    };

    @Test
    public void testA() throws Exception {
        StringProvider input = StringProvider.forArray(TEST_INPUT_A);
        Day19Medicine medicine = new Day19Medicine(input);
        assertThat(medicine.possibilities(), is(4));
    }

    @Test
    public void testB1() throws Exception {
        StringProvider input = StringProvider.forArray(TEST_INPUT_B);
        Day19Medicine medicine = new Day19Medicine(input);
        assertThat(medicine.stepsFromETo("HOH"), is(3));
    }

    @Test
    public void testB2() throws Exception {
        StringProvider input = StringProvider.forArray(TEST_INPUT_B);
        Day19Medicine medicine = new Day19Medicine(input);
        assertThat(medicine.stepsFromETo("HOHOHO"), is(6));
    }

}