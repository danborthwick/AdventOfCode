package y2015;

import util.StringProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7Circuit {

    public static class Circuit {
        private static final int MASK = 0xffff;

        Map<String, Gate> wires = new HashMap();

        public Circuit(StringProvider input) throws Exception {
            while (input.hasMore()) {
                String[] leftRight = input.next().split("\\s*\\->\\s*");
                String outputName = leftRight[1];
                wires.put(outputName, gateFromString(leftRight[0]));
            }
        }

        static final Pattern notPattern = Pattern.compile("NOT (\\w+)");
        static final Pattern binaryPattern = Pattern.compile("(\\w+)\\s+(\\w+)\\s+(\\w+)");

        private Gate gateFromString(String s) throws Exception {
            if (s.matches("\\d+")) {
                return makeConstGate(Integer.valueOf(s));
            }

            if (s.matches("\\w+")) {
                return makeAssignGate(s);
            }

            Matcher notMatcher = notPattern.matcher(s);
            if (notMatcher.matches()) {
                return makeLogicGate(notMatcher.group(1), null, Operator.NOT);
            }

            Matcher binaryMatcher = binaryPattern.matcher(s);
            if (!binaryMatcher.matches())
                throw new Exception("Unmatchable");

            return makeLogicGate(binaryMatcher.group(1), binaryMatcher.group(3), Operator.fromString(binaryMatcher.group(2)));
        }

        public String[] allWires() {
            List<String> result = new ArrayList(wires.size());
            for (Map.Entry<String, Gate> entry : wires.entrySet()) {
                result.add(entry.getKey() + ": " + entry.getValue().evaluate());
            }
            return result.toArray(new String[result.size()]);
        }

        public int wire(String name) {
            return wires.get(name).evaluate();
        }

        public void reset() {
            for (Gate gate : wires.values()) {
                gate.reset();
            }

        }

        public void assign(String name, int value) {
            wires.put(name, makeConstGate(value));
        }

        enum Operator {
            LSHIFT, RSHIFT, NOT, AND, OR;

            static Operator fromString(String s) {
                switch (s) {
                    case "LSHIFT": return LSHIFT;
                    case "RSHIFT": return RSHIFT;
                    case "NOT": return NOT;
                    case "AND": return AND;
                    case "OR": return OR;
                    default:
                        throw new IllegalArgumentException(s);
                }
            }
        }

        interface Gate {
            int evaluate();

            void reset();
        }

        Gate makeConstGate(int value) {
            return new Gate() {
                @Override
                public int evaluate() {
                    return value;
                }

                @Override
                public void reset() {}
            };
        }

        Gate makeLogicGate(String inputName1, String inputName2, Operator operator) {
            return new Gate() {
                int cached = -1;

                @Override
                public int evaluate() {
                    if (cached != -1)
                        return cached;

                    Gate input1 = gateForRHS(inputName1);
                    Gate input2 = inputName2 != null ? gateForRHS(inputName2) : null;

                    switch (operator) {
                        case LSHIFT:
                            cached = (input1.evaluate() << input2.evaluate()) & MASK;
                            break;

                        case RSHIFT:
                            cached = (input1.evaluate() >>> input2.evaluate()) & MASK;
                            break;

                        case NOT:
                            cached = ~input1.evaluate() & MASK;
                            break;

                        case AND:
                            cached = input1.evaluate() & input2.evaluate();
                            break;

                        case OR:
                            cached = input1.evaluate() | input2.evaluate();
                            break;
                    }

                    return cached;
                }

                @Override
                public void reset() {
                    cached = -1;
                }
            };
        }

        Gate makeAssignGate(String inputName) {
            return new Gate() {
                @Override
                public int evaluate() {
                    Gate input = wires.get(inputName);
                    return input.evaluate();
                }

                @Override
                public void reset() {}
            };
        }

        public Gate gateForRHS(String rhsString) {
            Gate gate = wires.get(rhsString);
            if (gate != null)
                return gate;

            if (rhsString.matches("\\d+"))
                return makeConstGate(Integer.valueOf(rhsString));

            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("Day7Input.txt");
        Circuit circuit = new Circuit(input);
        int a1 = circuit.wire("a");
        System.out.println("Wire a1: " + a1);

        circuit.reset();
        circuit.assign("b", a1);
        int a2 = circuit.wire("a");
        System.out.println("Wire a2: " + a2);
    }
}
