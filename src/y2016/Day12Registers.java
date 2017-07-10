package y2016;

import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12Registers {

    List<String> instructions;
    int[] registers = new int[4];

    static final Pattern PATTERN = Pattern.compile("(?<op>cpy|inc|dec|jnz) (?<p1>[\\w\\d\\-]+) ?(?<p2>[\\w\\d\\-]+)?");

    public Day12Registers(StringProvider input) throws Exception {
        instructions = new ArrayList<>();
        while (input.hasMore()) {
            instructions.add(input.next());
        }
    }

    public int execute() throws Exception {

        for (int counter = 0; counter < instructions.size(); counter++) {
            String instruction = instructions.get(counter);
            Matcher matcher = PATTERN.matcher(instruction);
            if (!matcher.matches()) { throw new Exception("Unable to parse " + instruction); }

            String p1 = matcher.group("p1");
            String p2 = matcher.group("p2");

            switch (matcher.group("op")) {
                case "cpy":
                    setRegister(p2, getRegister(p1));
                    break;

                case "inc":
                    setRegister(p1, getRegister(p1) + 1);
                    break;

                case "dec":
                    setRegister(p1, getRegister(p1) - 1);
                    break;

                case "jnz":
                    if (getRegister(p1) != 0) {
                        counter += Integer.parseInt(p2) - 1;
                    }
                    break;
            }
        }

        return getRegister("a");
    }

    private int getRegister(String name) {
        try {
            return Integer.parseInt(name);
        }
        catch (NumberFormatException e) {
            return registers[name.charAt(0) - 'a'];
        }
    }

    void setRegister(String name, int value) {
        registers[name.charAt(0) - 'a'] = value;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day12Input.txt");
        Day12Registers registers = new Day12Registers(input);
        System.out.println("Part 1: " + registers.execute());

        input = StringProvider.forFile("2016Day12Input.txt");
        Day12Registers part2 = new Day12Registers(input);
        part2.setRegister("c", 1);
        System.out.println("Part 2: " + part2.execute());
    }
}