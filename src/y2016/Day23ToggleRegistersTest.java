package y2016;

import org.junit.Test;
import util.StringProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day23ToggleRegistersTest {

    @Test
    public void day23() throws Exception {
        StringProvider input = StringProvider.forArray(new String[] {
                "cpy 2 a",
                "tgl a",
                "tgl a",
                "tgl a",
                "cpy 1 a",
                "dec a",
                "dec a"
        });

        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        assertThat(registers.execute(), is(3));
    }
    
    @Test
    public void isMultiplication() throws Exception {
        StringProvider input = StringProvider.forArray(new String[] {
                "cpy 7 a",
                "cpy 6 b",
                "cpy a d",
                "cpy 0 a",
                "cpy b c",
                "inc a",
                "dec c",
                "jnz c -2",
                "dec d",
                "jnz d -5"
        });

        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        assertThat(registers.execute(), is(42));
    }

    @Test
    public void replaceMultiplication() throws Exception {
        StringProvider input = StringProvider.forArray(new String[] {
                "cpy 7 a",
                "cpy 6 b",
                "cpy a d",
                "cpy 0 a",
                "cpy b c",
                "inc a",
                "dec c",
                "jnz c -2",
                "dec d",
                "jnz d -5"
        });

        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        registers.replaceInstruction(2, 9, "mul a b");

        assertThat(registers.execute(), is(42));
    }
    
    @Test
    public void day12() throws Exception {
        StringProvider input = StringProvider.forArray(new String[] {
                "cpy 41 a",
                "inc a",
                "inc a",
                "dec a",
                "jnz a 2",
                "dec a"
        });

        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        assertThat(registers.execute(), is(42));
    }
}