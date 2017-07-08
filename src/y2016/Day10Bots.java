package y2016;

import util.StringProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day10Bots {

    Map<Integer, Bot> bots = new HashMap<>();
    Map<Integer, Output> outputs = new HashMap<>();

    final static Pattern ASSIGN = Pattern.compile("value (?<value>\\d+) goes to bot (?<bot>\\d+)");
    final static Pattern GIVE = Pattern.compile("bot (?<from>\\d+) gives low to (?<lowType>output|bot) (?<lowId>\\d+) and high to (?<highType>output|bot) (?<highId>\\d+)");

    interface Node {
        int get(Node getter);
    }

    class Bot implements Node {
        int cachedLow = -1;
        int cachedHigh = -1;
        Node input1, input2;
        Node outputLow, outputHigh;

        public int get(Node getter) {
            ensureCache();
            return (getter == outputLow) ? cachedLow : cachedHigh;
        }

        public boolean matches(int low, int high) {
            ensureCache();
            return low == cachedLow && high == cachedHigh;
        }

        private void ensureCache() {
            if (cachedLow == -1) {
                cachedLow = input1.get(this);
                cachedHigh = input2.get(this);

                if (cachedHigh < cachedLow) {
                    int swap = cachedHigh;
                    cachedHigh = cachedLow;
                    cachedLow = swap;
                }
            }
        }

        public void addInput(Node input) {
            if (input1 == null) {
                input1 = input;
            }
            else {
                input2 = input;
            }
        }
    }

    class Input implements Node {
        int value;

        public Input(int value) {
            this.value = value;
        }

        @Override
        public int get(Node getter) {
            return value;
        }
    }

    class Output implements Node {
        Node input;

        @Override
        public int get(Node getter) {
            return input.get(this);
        }

        void addInput(Node input) { this.input = input; }
    }

    void prepare(StringProvider input) throws Exception {

        while (input.hasMore()) {
            String instruction = input.next();
            try {
                Matcher assign = ASSIGN.matcher(instruction);
                if (assign.matches()) {
                    assign(assign);
                } else {
                    Matcher give = GIVE.matcher(instruction);
                    if (!give.matches()) {
                        throw new Exception("Unknown instruction " + instruction);
                    }

                    give(give);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error in " + instruction + e.getMessage());
            }
        }
    }

    int findComparer(int lowCompare, int highCompare) throws Exception {
        for (Map.Entry<Integer, Bot> entry : bots.entrySet()) {
            if (entry.getValue().matches(lowCompare, highCompare)) {
                return entry.getKey();
            }
        }

        throw new Exception("Bot not found");
    }

    int product() {
        int product = 1;
        for (int i=0; i <= 2; i++) {
            Output output = getOutput(i);
            product *= output.get(null);
        }
        return product;
    }

    private void assign(Matcher instruction) {
        int value = parseInt(instruction.group("value"));
        int botId = parseInt(instruction.group("bot"));

        Bot bot = getBot(botId);
        bot.addInput(new Input(value));
    }

    private Bot getBot(int id) {
        return bots.computeIfAbsent(id, key -> new Bot());
    }

    private Output getOutput(int id) {
        return outputs.computeIfAbsent(id, key -> new Output());
    }

    private void give(Matcher instruction) {
        int fromId = parseInt(instruction.group("from"));
        String lowType = instruction.group("lowType");
        int lowId = parseInt(instruction.group("lowId"));
        String highType = instruction.group("highType");
        int highId = parseInt(instruction.group("highId"));

        Bot from = getBot(fromId);

        if (lowType.equals("bot")) {
            Bot to = getBot(lowId);
            from.outputLow = to;
            to.addInput(from);
        }
        else {
            Output to = getOutput(lowId);
            from.outputLow = to;
            to.addInput(from);
        }

        if (highType.equals("bot")) {
            Bot to = getBot(highId);
            from.outputHigh = to;
            to.addInput(from);
        }
        else {
            Output to = getOutput(highId);
            from.outputHigh = to;
            to.addInput(from);
        }
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day10Input.txt");
        Day10Bots bots = new Day10Bots();
        bots.prepare(input);
        System.out.println("Comparer: " + bots.findComparer(17, 61));
        System.out.println("Product: " + bots.product());
    }
}
