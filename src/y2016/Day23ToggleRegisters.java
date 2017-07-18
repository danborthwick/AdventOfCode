package y2016;

import util.StringProvider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day23ToggleRegisters {

    List<Instruction> instructions;
    int[] registers = new int[4];

    static final Pattern PATTERN = Pattern.compile("(?<op>cpy|inc|dec|jnz|tgl) (?<p1>[\\w\\d\\-]+) ?(?<p2>[\\w\\d\\-]+)?");

    public Day23ToggleRegisters(StringProvider input) throws Exception {
        instructions = input.asList().stream().map(s -> {
            try {
                return new Instruction(s);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    class Instruction {
        String operation;
        private int const1 = NOT_SET, const2 = NOT_SET;
        private int reg1 = NOT_SET, reg2 = NOT_SET;

        static final int NOT_SET = Integer.MIN_VALUE;

        Instruction(String string) throws Exception {
            Matcher matcher = PATTERN.matcher(string);
            if (!matcher.matches()) { throw new Exception("Unable to parse " + string); }

            operation = matcher.group("op");
            String p1 = matcher.group("p1");
            String p2 = matcher.group("p2");

            try {
                const1 = parseInt(p1);
            }
            catch (NumberFormatException nfe) {
                reg1 = p1.charAt(0) - 'a';
            }

            if (p2 != null) {
                try {
                    const2 = parseInt(p2);
                } catch (NumberFormatException nfe) {
                    reg2 = p2.charAt(0) - 'a';
                }
            }
        }

        int p1() {
            return const1 == NOT_SET ? registers[reg1] : const1;
        }

        int p2() {
            return const2 == NOT_SET ? registers[reg2] : const2;
        }

        int address1() {
            return reg1;
        }

        int address2() {
            return reg2;
        }

        void toggle() {
            if (singleParam()) {
                operation = operation.equals("inc") ? "dec" : "inc";
            }
            else {
                operation = operation.equals("jnz") ? "cpy" : "jnz";
            }
        }

        private boolean singleParam() {
            return const2 == NOT_SET && reg2 == NOT_SET;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(operation).append(" ");
            if (const1 == NOT_SET) {
                builder.append("[").append((char)('a' + reg1)).append("]");
            }
            else {
                builder.append(const1);
            }

            if (const2 == NOT_SET) {
                if (reg2 != NOT_SET) {
                    builder.append(" [").append((char) ('a' + reg2)).append("]");
                }
            }
            else {
                builder.append(" ").append(const2);
            }
            return builder.toString();
        }
    }

    public int execute() throws Exception {

        for (int counter = 0; counter < instructions.size(); counter++) {
            Instruction instruction = instructions.get(counter);

            switch (instruction.operation) {
                case "cpy":
                    setRegister(instruction.address2(), instruction.p1());
                    break;

                case "inc":
                    setRegister(instruction.address1(), instruction.p1() + 1);
                    break;

                case "dec":
                    setRegister(instruction.address1(), instruction.p1() - 1);
                    break;

                case "jnz":
                    if (instruction.p1() != 0) {
                        counter += instruction.p2() - 1;
                    }
                    break;

                case "tgl":
                    int target = counter + instruction.p1();
                    if (0 <= target && target < instructions.size()) {
                        Instruction targetInstruction = instructions.get(target);
                        targetInstruction.toggle();
                    }
                    break;

                default:
                    throw new Exception("Unknown operation: " + instruction.operation);
            }
        }

        return registers[0];
    }

    void setRegister(int r, int value) {
        registers[r] = value;
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day23Input.txt");
        Day23ToggleRegisters registers = new Day23ToggleRegisters(input);
        registers.setRegister(0, 7);
        System.out.println("Part 1: " + registers.execute());

        input = StringProvider.forFile("2016Day23Input.txt");
        registers = new Day23ToggleRegisters(input);
        registers.setRegister(0, 12);
        System.out.println("Part 2: " + registers.execute());
    }
}