package y2016;

import util.StringProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day25ClockSignal {

    List<Instruction> instructions;
    int[] registers = new int[4];

    static final Pattern PATTERN = Pattern.compile("(?<op>cpy|inc|dec|jnz|mul|nop|out) (?<p1>[\\w\\d\\-]+) ?(?<p2>[\\w\\d\\-]+)?");
    private int counter;
    private int output, lastOutput;

    static final int NOT_SET = Integer.MIN_VALUE;

    public Day25ClockSignal(StringProvider input) throws Exception {
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

    static class State {
        final int counter, lastOutput, r0, r1, r2, r3;

        State(int counter, int lastOutput, int r0, int r1, int r2, int r3) {
            this.counter = counter;
            this.lastOutput = lastOutput;
            this.r0 = r0;
            this.r1 = r1;
            this.r2 = r2;
            this.r3 = r3;
        }

        @Override
        public int hashCode() {
            return Objects.hash(counter, lastOutput, r0, r1, r2, r3);
        }

        @Override
        public boolean equals(Object obj) {
            State other = (State) obj;

            return this.counter == other.counter
                    && this.lastOutput == other.lastOutput
                    && this.r0 == other.r0
                    && this.r1 == other.r1
                    && this.r2 == other.r2
                    && this.r3 == other.r3;
        }
    }

    void step() throws Exception {
        Instruction instruction = instructions.get(counter);
        output = NOT_SET;

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

            case "mul":
                setRegister(instruction.address1(), instruction.p1() * instruction.p2());
                break;

            case "jnz":
                if (instruction.p1() != 0) {
                    counter += instruction.p2() - 1;
                }
                break;

            case "nop":
                break;

            case "out":
                output = instruction.p1();
                lastOutput = output;
                break;

            default:
                throw new Exception("Unknown operation: " + instruction.operation);
        }

        counter++;
    }

    State getState() {
        return new State(counter, lastOutput, registers[0], registers[1], registers[2], registers[3]);
    }

    void setRegister(int r, int value) {
        registers[r] = value;
    }

    void reset() {
        counter = 0;
        lastOutput = 0;
        for (int r = 0; r < registers.length; r++) {
            setRegister(r, 0);
        }
    }

    int firstClockSignal() throws Exception {
        for (int initializer = 0; ; initializer++) {
            Set<State> states = new HashSet<>();

            reset();
            setRegister(0, initializer);
            int nextOutput = 0;
            int outputCount = 0;

            while (true) {
                if (counter < 0 || counter >= instructions.size()) {
                    System.out.println("Exit");
                    break;
                }

                step();
                State state = getState();
                if (states.contains(getState()) && (outputCount >= 2)) {
                    return initializer;
                }

                states.add(state);
                if (output != NOT_SET) {
                    if (output != nextOutput) {
                        System.out.println("Bad output " + output);
                        break;
                    }
                    System.out.println("Init " + initializer + " out " + output);
                    nextOutput = (output == 1) ? 0 : 1;
                    outputCount++;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day25Input.txt");
        Day25ClockSignal clock = new Day25ClockSignal(input);
        System.out.println("Part 1: " + clock.firstClockSignal());
    }

}
