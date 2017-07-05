package y2015;
import util.*;

public class Day8Escaping {
    public static int codeMemoryDifference(StringProvider input) throws Exception
    {
        int totalCodeLength = 0;
        int totalMemoryLength = 0;

        while (input.hasMore()) {
            String s = input.next();
            totalCodeLength += codeLength(s);
            totalMemoryLength += memoryLength(s);
        }

        return totalCodeLength - totalMemoryLength;
    }

    public static int encodedCodeDifference(StringProvider input) throws Exception {
        int totalEncodedLength = 0;
        int totalCodeLength = 0;

        while (input.hasMore()) {
            String s = input.next();
            totalCodeLength += codeLength(s);
            totalEncodedLength += encodedLength(s);
        }

        return totalEncodedLength - totalCodeLength;
    }

    public static int codeLength(String s) {
        return s.length();
    }

    public static int memoryLength(String s) {
        String withoutQuotes = s.substring(1, s.length() - 1);
        String replaced = withoutQuotes.replaceAll("\\\\(\"|\\\\|x[a-fA-F0-9]{2})", ".");
        return replaced.length();
    }

    public static int encodedLength(String s) {
        String replaced = s.replaceAll("(\\\\|\")", "\\$1");
        String quoted = "\"" + replaced + "\"";
        return quoted.length();
    }


    public static void main(String[] args) throws Exception
    {
        StringProvider input = StringProvider.forFile("Day8Input.txt");
        System.out.println("CodeMemoryDifference: " + codeMemoryDifference(input));

        StringProvider input2 = StringProvider.forFile("Day8Input.txt");
        System.out.println("EncodedCodeDifference: " + encodedCodeDifference(input2));
    }
}
