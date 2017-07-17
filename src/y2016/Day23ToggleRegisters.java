package y2016;

import util.StringProvider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day23ToggleRegisters {

    List<String> instructions;
    int[] registers = new int[4];

    static final Pattern PATTERN = Pattern.compile("(?<op>cpy|inc|dec|jnz|tgl) (?<p1>[\\w\\d\\-]+) ?(?<p2>[\\w\\d\\-]+)?");

    public Day23ToggleRegisters(StringProvider input) throws Exception {
        instructions = input.asList();
    }

    class Instruction {
        final String operation, p1, p2;

        Instruction(String string) throws Exception {
            Matcher matcher = PATTERN.matcher(string);
            if (!matcher.matches()) { throw new Exception("Unable to parse " + string); }

            operation = matcher.group("op");
            p1 = matcher.group("p1");
            p2 = matcher.group("p2");
        }
    }

    public int execute() throws Exception {

        for (int counter = 0; counter < instructions.size(); counter++) {
            String instructionString = instructions.get(counter);
            Instruction instruction = new Instruction(instructionString);

            switch (instruction.operation) {
                case "cpy":
                    setRegister(instruction.p2, getRegister(instruction.p1));
                    break;

                case "inc":
                    setRegister(instruction.p1, getRegister(instruction.p1) + 1);
                    break;

                case "dec":
                    setRegister(instruction.p1, getRegister(instruction.p1) - 1);
                    break;

                case "jnz":
                    if (getRegister(instruction.p1) != 0) {
                        counter += getRegister(instruction.p2) - 1;
                    }
                    break;

                case "tgl":
                    int target = counter + getRegister(instruction.p1);
                    if (0 <= target && target < instructions.size()) {
                        Instruction targetInstruction = new Instruction(instructions.get(target));
                        instructions.set(target, toggle(targetInstruction));
                    }

                    break;
            }
        }

        return getRegister("a");
    }

    private String toggle(Instruction instruction) {
        if (instruction.p2 == null) {
            String operation = instruction.operation.equals("inc") ? "dec" : "inc";
            return String.format("%s %s", operation, instruction.p1);
        }
        else {
            String operation = instruction.operation.equals("jnz") ? "cpy" : "jnz";
            return String.format("%s %s %s", operation, instruction.p1, instruction.p2);
        }
    }

    private int getRegister(String name) {
        try {
            return parseInt(name);
        }
        catch (NumberFormatException e) {
            return registers[name.charAt(0) - 'a'];
        }
    }

    void setRegister(String name, int value) {
        registers[name.charAt(0) - 'a'] = value;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day23Input.txt");
        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        System.out.println("Part 1: " + registers.execute());
    }
}