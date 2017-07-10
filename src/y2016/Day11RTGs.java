package y2016;

import util.Chooser;
import util.StringProvider;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11RTGs {

    public static final int INVALID_STATE = -1;
    public static final int ITEM_MASK = 0b11;

    int initialState;
    final int elementCount;
    final int stateCount;
    final int elevatorShift;
    final List<String> elements;

    static class Direction {
        static final int UP = 1;
        static final int DOWN = -1;
    }

    static class Floor {
        int floorNumber;
        Set<String> chips = new HashSet<>();
        Set<String> generators = new HashSet<>();

        static final Pattern PATTERN = Pattern.compile("(?<element>\\w+)(?<type> generator|-compatible microchip)");

        public Floor(int floorNumber, String description, List<String> elements) {
            this.floorNumber = floorNumber;
            Matcher matcher = PATTERN.matcher(description);

            while (matcher.find()) {
                String type = matcher.group("type");
                String element = matcher.group("element");
                if (!elements.contains(element)) {
                    elements.add(element);
                }

                Set<String> set = type.equals(" generator") ? generators : chips;
                set.add(element);
            }
        }


        public int toState(List<String> elements) {
            int state = 0;

            for (String generator : generators) {
                int index = elements.indexOf(generator);
                state |= floorNumber << (index * 2);
            }

            for (String chip : chips) {
                int index = elements.indexOf(chip);
                state |= floorNumber << ((index + elements.size()) * 2);
            }

            return state;
        }
    }

    public Day11RTGs(StringProvider input) throws Exception {
        elements = new ArrayList<>();

        for (int floorNumber = 0; input.hasMore(); floorNumber++) {
            Floor floor = new Floor(floorNumber, input.next(), elements);
            initialState |= floor.toState(elements);
        }

        elementCount = elements.size();
        elevatorShift = elementCount * 2;
        stateCount = (int) Math.pow(4, elevatorShift + 1);
    }

    public int minSteps() {
        int targetState = stateCount - 1;
        List<Pair> choices = getChoices();

        Set<Integer> allVisited = new HashSet<>();
        Set<Integer> visitedLastMove = new HashSet<>();
        visitedLastMove.add(initialState);
        allVisited.add(initialState);

        int move = 0;
        while (!allVisited.contains(targetState)) {
            Set<Integer> visitedThisMove = new HashSet<>();

            for (Integer fromState : visitedLastMove) {
                getNextStates(fromState, choices, visitedThisMove, allVisited);
            }

            visitedLastMove = visitedThisMove;
            move++;
        }

        return move;
    }

    class Pair {
        public int first, second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    private Map<Integer, Set<Integer>> calculateNextStates() {
        System.out.println("Calculate next states for " + stateCount + " states");
        long percent = -1;
        long startTime= System.currentTimeMillis();
        final List<Pair> choices = getChoices();

        Map<Integer, Set<Integer>> nextStates = new HashMap<>();

        for (int state = 0; state < stateCount; state++) {
            if (!isSafe(state)) continue;

            long percentNow = ((long) state * 1000L) / stateCount;
            if (percentNow != percent) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000L;
                System.out.println("Completed " + percentNow + "% in " + elapsed + " seconds");
                percent = percentNow;
            }

            Set<Integer> stateNextStates = getNextStates(state, choices);
            nextStates.put(state, stateNextStates);
        }
        return nextStates;
    }

    Set<Integer> getNextStates(int state, List<Pair> choices) {
        Set<Integer> nextStates = new HashSet<>(16);
        getNextStates(state, choices, nextStates, new HashSet<>());
        return nextStates;
    }

    void getNextStates(int state, List<Pair> choices, Set<Integer> nextStates, Set<Integer> visited) {
        int fromFloor = getFloor(state, elevatorShift);

        for (Pair choice : choices) {
            if (fromFloor > 0) {
                int toState = move(fromFloor, Direction.DOWN, state, choice.first, choice.second);
                if (toState != INVALID_STATE && !visited.contains(toState)) {
                    nextStates.add(toState);
                    visited.add(toState);
                }
            }

            if (fromFloor < 3) {
                int toState = move(fromFloor, Direction.UP, state, choice.first, choice.second);
                if (toState != INVALID_STATE && !visited.contains(toState)) {
                    nextStates.add(toState);
                    visited.add(toState);
                }
            }
        }
    }


    List<Pair> getChoices() {
        //TODO: Prune generator/generator pairs here
        //TODO: Prune incompatible chip/generator pairs here
        final List<Integer> elements = IntStream.range(0, elevatorShift + 1).mapToObj(i -> new Integer(i)).collect(Collectors.toList());
        Chooser<Integer> chooser = new Chooser<>(elements);
        final int maxMoves = (int) chooser.numberOfChoices(2) * 2;
        final List<Pair> choices = new ArrayList<>(maxMoves);
        chooser.choose(2, choice -> {
            Pair pair = new Pair(choice.get(0), choice.get(1));
            if (pair.first < elementCount && pair.second >= elementCount && pair.second != elevatorShift && ((pair.second - pair.first) != elementCount)) {
                return null;
            }
            choices.add(pair);
            return null;
        });
        return choices;
    }

    private int move(int fromFloor, int direction, int fromState, int firstElement, int secondElement) {

        if (getFloor(fromState, firstElement) != fromFloor) { return INVALID_STATE; }
        if (getFloor(fromState, secondElement) != fromFloor) { return INVALID_STATE; }
        if (!compatible(firstElement, secondElement)) { return INVALID_STATE; }

        int toFloor = fromFloor + direction;
        int toState = setFloor(fromState, firstElement, toFloor);
        toState = setFloor(toState, secondElement, toFloor);
        toState = setFloor(toState, elevatorShift, toFloor);    // Set elevator position

        if (isSafe(toState)) {
            return toState;
        }
        return INVALID_STATE;
    }

    private boolean compatible(int firstElement, int secondElement) {
        // Chooser ensures first < second
        if (firstElement < elementCount) {
            // First is gen
            if (secondElement < elementCount) {
                // Two generators
                return true;
            }
            else if ((secondElement - firstElement) == elementCount) {
                // Chip/gen pair
                return true;
            }
            else {
                return secondElement == elevatorShift;  // Single generator
            }
        }
        else {
            return true;
        }
    }

    private boolean isSafe(int state) {
        for (int chip = elementCount; chip < elevatorShift; chip++) {
            int chipFloor = getFloor(state, chip);
            if (chipFloor == getFloor(state, chip - elementCount)) { continue; }    // Powered

            for (int generator = 0; generator < elementCount; generator++) {
                if (chipFloor == getFloor(state, generator)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getFloor(int state, int element) {
        return (state >> (2 * element)) & ITEM_MASK;
    }

    private int setFloor(int state, int element, int floor) {
        int shift = (2 * element);
        state &= ~(ITEM_MASK << shift);
        return state | floor << shift;
    }

    void traceState(int state, String msg) {
        System.out.println(msg + ":");
        List<String> elementNames = elements.stream().map(fullname -> fullname.substring(0, 2)).collect(Collectors.toList());

        for (int floor = 3; floor >= 0; floor--) {
            StringBuilder builder = new StringBuilder("F");
            builder.append(floor + 1).append(" ");
            builder.append(getFloor(state, elevatorShift) == floor ? " E " : " . ");

            for (int element = 0; element < elementCount; element++) {
                if (getFloor(state, element) == floor) {
                    builder.append(elementNames.get(element)).append("G ");
                }
                else {
                    builder.append(".   ");
                }

                if (getFloor(state, element + elementCount) == floor) {
                    builder.append(elementNames.get(element)).append("M ");
                }
                else {
                    builder.append(".   ");
                }
            }
            System.out.println(builder);
        }
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day11Input.txt");
        Day11RTGs rtgs = new Day11RTGs(input);
        System.out.println("Min steps: " + rtgs.minSteps());

        StringProvider input2 = StringProvider.forArray(new String[] {
                "The first floor contains a polonium generator, a thulium generator, a thulium-compatible microchip, a promethium generator, a ruthenium generator, " +
                        "a ruthenium-compatible microchip, a cobalt generator, and a cobalt-compatible microchip." +
                        " and a elerium generator and a elerium-compatible microchip" +
                        " and a dilithium generator and a dilithium-compatible microchip",

                        "The second floor contains a polonium-compatible microchip and a promethium-compatible microchip.",
                        "The third floor contains nothing relevant.",
                        "The fourth floor contains nothing relevant."
        });

        Day11RTGs part2 = new Day11RTGs(input2);
        System.out.println("Min steps 2: " + part2.minSteps());
    }
}
