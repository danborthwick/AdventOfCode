package y2016;

import org.apache.commons.lang3.ArrayUtils;
import util.StringProvider;

public class Day21Scrambler {
    private String initialState;
    char[] state;

    Day21Scrambler(String state) {
        initialState = state;
        this.state = state.toCharArray();
    }

    String run(StringProvider input) throws Exception {
        while (input.hasMore()) {
            step(input.next());
        }
        return new String(state);
    }

    String stepAndGetState(String instruction) throws Exception {
        step(instruction);
        return new String(state);
    }

    public String reverseAndGetState(String instruction) throws Exception {
        stepReverse(instruction);
        return new String(state);
    }

    int getInt(String[] parts, int group) {
        return Integer.parseInt(parts[group]);
    }

    char getChar(String[] parts, int group) {
        return parts[group].charAt(0);
    }

    int getDirection(String[] parts, int group) throws Exception {
        switch (parts[group]) {
            case "left":
                return -1;

            case "right":
                return 1;

            default:
                throw new Exception("Unknown direction " + parts[group]);
        }
    }

    void step(String instruction) throws Exception {
        stepHelper(instruction, true);
    }

    void stepReverse(String instruction) throws Exception {
        stepHelper(instruction, false);
    }

    private void stepHelper(String instruction, boolean forward) throws Exception {
        String[] parts = instruction.split(" ");
        switch (parts[0]) {
            case "swap":
                switch (parts[1]) {
                    case "position":
                        swapPosition(getInt(parts, 2), getInt(parts, 5));
                        break;

                    case "letter":
                        swapLetter(getChar(parts, 2), getChar(parts, 5));
                        break;

                    default:
                        throw new Exception("Can't parse " + instruction);
                }
                break;

            case "rotate":
                switch (parts[1]) {
                    case "based":
                        if (forward) {
                            rotateBasedOnPositionOf(getChar(parts, 6));
                        }
                        else {
                            //Unimplemented
                        }
                        break;

                    default:
                        int direction = getDirection(parts, 1);
                        if (!forward) {
                            direction = -direction;
                        }
                        rotate(direction, getInt(parts, 2));
                        break;
                }
                break;

            case "reverse":
                reverse(getInt(parts, 2), getInt(parts, 4));
                break;

            case "move":
                if (forward) {
                    move(getInt(parts, 2), getInt(parts, 5));
                }
                else {
                    move(getInt(parts, 5), getInt(parts, 2));
                }
                break;

            default:
                throw new Exception("Can't parse " + instruction);
        }
    }

    private void swapPosition(int posX, int posY) {
        char swap = state[posX];
        state[posX] = state[posY];
        state[posY] = swap;
    }

    private void swapLetter(char letterX, char letterY) {
        replace(letterX, '$');
        replace(letterY, letterX);
        replace('$', letterY);
    }

    private void replace(char from, char to) {
        for (int i=0; i < state.length; i++) {
            if (state[i] == from) {
                state[i] = to;
            }
        }
    }

    private void rotate(int direction, int distance) {
        for (int d =0 ; d < distance; d++) {
            switch (direction) {
                case -1: //Left
                    char swap = state[0];
                    for (int i = 1; i < state.length; i++) {
                        state[i - 1] = state[i];
                    }
                    state[state.length - 1] = swap;
                    break;

                case 1: //Right
                    swap = state[state.length - 1];
                    for (int i = state.length - 1; i >= 1; i--) {
                        state[i] = state[i - 1];
                    }
                    state[0] = swap;
                    break;
            }
        }
    }

    private void rotateBasedOnPositionOf(char c) {
        int position = ArrayUtils.indexOf(state, c);
        int distance = position + 1 + ((position >= 4) ? 1 : 0);
        rotate(1, distance);
    }

    private void reverse(int posX, int posY) {
        for (int from = posX, to = posY; from < to; from++, to--) {
            char swap = state[from];
            state[from] = state[to];
            state[to] = swap;
        }
    }

    private void move(int from, int to) {
        char swap = state[from];
        if (from < to) {
            for (int i = from; i < to; i++) {
                state[i] = state[i + 1];
            }
        }
        else {
            for (int i = from; i > to; i--) {
                state[i] = state[i - 1];
            }
        }
        state[to] = swap;
    }

    public static void main(String[] args) throws Exception {
        Day21Scrambler scrambler = new Day21Scrambler("abcdefgh");
        StringProvider input = StringProvider.forFile("2016Day21Input.txt");
        System.out.println("Part1: " + scrambler.run(input));
    }
}
