package y2016;

import util.StringProvider;

import java.util.ArrayList;
import java.util.List;

public class Day7IP7 {
    public static boolean supportsTLS(String address) {
        String[] parts = address.split("[\\[\\]]");
        boolean supports = false;

        for (int i=0; i < parts.length; i++) {
            if (isAbba(parts[i])) {
                if ((i % 2) == 0) {
                    supports = true;
                }
                else {
                    supports = false;
                    break;
                }
            }
        }

        return supports;
    }

    private static boolean isAbba(String s) {
        for (int i = 3; i < s.length(); i++) {
            if ((s.charAt(i) == s.charAt(i - 3))
                    && (s.charAt(i - 1) == s.charAt(i - 2))
                    && (s.charAt(i) != s.charAt(i - 1))) {
                return true;
            }
        }
        return false;
    }

    private static int countTLS(StringProvider input) throws Exception {
        int count = 0;
        while (input.hasMore()) {
            if (supportsTLS(input.next())) {
                count++;
            }
        }
        return count;
    }

    private static List<String> babs(String s) {
        List<String> result = new ArrayList<>();

        for (int i = 2; i < s.length(); i++) {
            if ((s.charAt(i) == s.charAt(i - 2))
                    && (s.charAt(i) != s.charAt(i - 1))) {
                String bab = String.format("%c%c%c", s.charAt(i-1), s.charAt(i), s.charAt(i - 1));
                result.add(bab);
            }
        }
        return result;
    }

    public static boolean supportsSSL(String address) {
        String[] parts = address.split("[\\[\\]]");
        List<String> babs = new ArrayList<>();

        for (int i=0; i < parts.length; i += 2) {
            babs.addAll(babs(parts[i]));
        }

        for (int i=1; i < parts.length; i += 2) {
            for (String bab : babs) {
                if (parts[i].contains(bab)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int countSSL(StringProvider input) throws Exception {
        int count = 0;
        while (input.hasMore()) {
            if (supportsSSL(input.next())) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("TLS: " + countTLS(makeInput()));
        System.out.println("SSL: " + countSSL(makeInput()));
    }

    private static StringProvider makeInput() throws Exception {
        return StringProvider.forFile("2016Day7Input.txt");
    }
}
