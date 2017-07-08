package y2016;

import util.StringProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day10Bots {
    class Bot {
        public int low = -1;
        public int high = -1;

        public void assign(int value) {
            if (low == -1) {
                low = value;
            }
            else if (value < low) {
                high = low;
                low = value;
            }
            else {
                high = value;
            }
        }
    }

    Map<Integer, Bot> bots = new HashMap<>();

    final static Pattern ASSIGN = Pattern.compile("value (?<value>\\d+) goes to bot (?<bot>\\d+)");
    final static Pattern GIVE = Pattern.compile("bot (?<from>\\d+) gives low to (?<lowType>output|bot) (?<lowId>\\d+) and high to (?<highType>output|bot) (?<highId>\\d+)");

    int findComparer(StringProvider input, int lowCompare, int highCompare) throws Exception {

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

                    int bot = give(give, lowCompare, highCompare);
                    if (bot != -1) {
                        return bot;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error in " + instruction + e.getMessage());
            }
        }

        throw new Exception("Bot not found");
    }

    private void assign(Matcher instruction) {
        int value = parseInt(instruction.group("value"));
        int botId = parseInt(instruction.group("bot"));

        Bot bot = getBot(botId);
        bot.assign(value);
    }

    private Bot getBot(int id) {
        return bots.computeIfAbsent(id, key -> new Bot());
    }

    private int give(Matcher instruction, int lowCompare, int highCompare) {
        int fromId = parseInt(instruction.group("from"));
        String lowType = instruction.group("lowType");
        int lowId = parseInt(instruction.group("lowId"));
        String highType = instruction.group("highType");
        int highId = parseInt(instruction.group("highId"));

        Bot from = bots.get(fromId);

        if (from.low == lowCompare && from.high == highCompare) {
            return fromId;
        }

        if (lowType.equals("bot")) {
            getBot(lowId).assign(from.low);
        }

        if (highType.equals("bot")) {
            getBot(highId).assign((from.high));
        }

        return -1;
    }


    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day10Input.txt");
        System.out.println("Bot " + new Day10Bots().findComparer(input, 17, 61));
    }
}
