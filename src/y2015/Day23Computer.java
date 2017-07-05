package y2015;

import util.StringProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23Computer {

    List<String> program = new ArrayList<>();
    int[] registers = new int[2];

    static final Pattern pattern = Pattern.compile("(?<instruction>\\w\\w\\w) (?<register>\\w)?(?:, )?(?<offset>[\\+\\-]\\d+)?");

    public Day23Computer(StringProvider input) throws Exception {
        while (input.hasMore()) {
            program.add(input.next());
        }
    }

    public void run() {
        int counter = 0;
        while (counter >= 0 && counter < program.size()) {
            String line = program.get(counter);
            Matcher matcher = pattern.matcher(line);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Unmatched instruction: " + line);
            }

            String registerString = matcher.group("register");
            int register = (registerString != null) ? registerString.charAt(0) - 'a' : -1;
            String offsetString = matcher.group("offset");
            int offset = (offsetString != null) ? Integer.valueOf(offsetString) : 1;

            switch (matcher.group("instruction")) {
                case "hlf":
                    registers[register] /= 2;
                    break;

                case "tpl":
                    registers[register] *= 3;
                    break;

                case "inc":
                    registers[register]++;
                    break;

                case "jmp":
                    break;

                case "jie":
                    if (!isEven(registers[register])) {
                        offset = 1;
                    }
                    break;

                case "jio":
                    if (registers[register] != 1) {
                        offset = 1;
                    }
                    break;
            }

            counter += offset;
        }
    }

    private static boolean isEven(int x) {
        return (x % 2) == 0;
    }

    public int getRegister(char name) {
        return registers[name - 'a'];
    }

    public void setRegister(char name, int value) {
        registers[name - 'a'] = value;
    }

    public void reset() {
        Arrays.fill(registers, 0);
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day23Input.txt");
        Day23Computer computer = new Day23Computer(input);

        computer.run();
        System.out.println("Initial A: 0, Register B: " + computer.getRegister('b'));

        computer.reset();
        computer.setRegister('a', 1);
        computer.run();
        System.out.println("Initial A: 1, Register B: " + computer.getRegister('b'));
    }
}
