package y2015;

import util.StringProvider;
import java.util.HashSet;
import java.util.Set;

public class Day3RoboSanta {

    public static class Santa {
        public int x = 0;
        public int y = 0;
    }

    public static long getWrappingPaper() throws Exception {
        StringProvider input = StringProvider.forFile("Day3Input.txt");
        return getRecipientCount(input.next());
    }

    public static int getRecipientCount(String input) throws Exception {
        Set<String> recipients = new HashSet();

        Santa santa = new Santa();
        Santa roboSanta = new Santa();

        setRecipient(recipients, 0, 0);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Santa deliverer = (i % 2 == 0) ? santa : roboSanta;
            deliver(deliverer, c);
            setRecipient(recipients, deliverer.x, deliverer.y);
        }

        return recipients.size();
    }

    public static void deliver(Santa santa, char c) throws Exception {
        switch (c)
        {
            case '<': santa.x--; break;
            case '>': santa.x++; break;
            case '^': santa.y--; break;
            case 'v': santa.y++; break;
            default: throw new Exception("Unexpected input " + c);
        }
    }

    private static void setRecipient(Set<String> recipients, int x, int y) {
        String position = String.valueOf(x) + "," + y;
        recipients.add(position);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Recipients " + getWrappingPaper());
    }
}
