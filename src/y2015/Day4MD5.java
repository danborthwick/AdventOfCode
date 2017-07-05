package y2015;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4MD5 {
    public static int getLowestNumberWithZeroHash(String secretKey, int minLeadingZeroes) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance("MD5");

        for (int i=1; ; i++) {
            String message = secretKey + i;
            byte[] bytesOfMessage = message.getBytes();

            byte[] digest = md.digest(bytesOfMessage);
            if (leadingZeroes(digest) >= minLeadingZeroes)
                return i;
        }
    }

    private static int leadingZeroes(byte[] digest) {
        int result = 0;

        for (byte b : digest) {

            if ((b & 0xf0) == 0)
                result++;
            else
                break;

            if ((b & 0x0f) == 0)
                result++;
            else
                break;
        }

        return result;
    }
}
