package y2016;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day5Hashes {

    public static String password(String doorId) throws NoSuchAlgorithmException {
        List<Character> password = new ArrayList<>(8);

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        for (int index = 0; password.size() < 8; index++) {
            String combined = doorId + index;
            byte[] digest = md5.digest(combined.getBytes());
            String hash = DatatypeConverter.printHexBinary(digest);
            if (hash.startsWith("00000")) {
                password.add(hash.toLowerCase().charAt(5));
            }
        }

        StringBuilder builder = new StringBuilder(8);
        for (Character c : password) {
            builder.append(c);
        }
        return builder.toString();
    }

    public static String passwordUnordered(String doorId) throws NoSuchAlgorithmException {
        char[] password = new char[8];

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        for (int index = 0; true; index++) {
            String combined = doorId + index;
            byte[] digest = md5.digest(combined.getBytes());
            String hash = DatatypeConverter.printHexBinary(digest);
            if (hash.startsWith("00000")) {
                try {
                    int position = parseInt(String.valueOf(hash.charAt(5)));
                    if ((position < 8) && (password[position] == 0)) {
                        password[position] = hash.toLowerCase().charAt(6);

                        String passwordString = new String(password);
                        if (!passwordString.contains(zeroChar)) {
                            return passwordString;
                        }
                    }
                }
                catch (NumberFormatException nfe) {}
            }
        }
    }

    public static String zeroChar = new String(new char[] { 0 });

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Password: " + password("abbhdwsy"));
        System.out.println("Unordered: " + passwordUnordered("abbhdwsy"));
    }
}
