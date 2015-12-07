import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Day3Santa {

    public static long getWrappingPaper() throws Exception {
        StringProvider input = StringProvider.forFile("Day3Input.txt");
        return getRecipientCount(input.next());
    }

    public static int getRecipientCount(String input) throws Exception {
        Set<String> recipients = new HashSet();

        int currentX = 0;
        int currentY = 0;
        setRecipient(recipients, 0, 0);
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i))
            {
                case '<': currentX--; break;
                case '>': currentX++; break;
                case '^': currentY--; break;
                case 'v': currentY++; break;
                default: throw new Exception("Unexpected input " + input.charAt(i));
            }
            setRecipient(recipients, currentX, currentY);
        }

        return recipients.size();
    }

    private static void setRecipient(Set<String> recipients, int x, int y) {
        String position = String.valueOf(x) + "," + y;
        recipients.add(position);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Recipients " + getWrappingPaper());
    }
}
