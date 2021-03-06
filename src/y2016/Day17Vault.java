package y2016;

import java.security.MessageDigest;
import java.util.Arrays;

public class Day17Vault {
    private final MessageDigest md5;
    private String seed;
    int shortestLength = Integer.MAX_VALUE;
    int longestLength = Integer.MIN_VALUE;
    String shortest, longest;

    Day17Vault(String seed) throws Exception {
        this.seed = seed;
        md5 = MessageDigest.getInstance("MD5");

        byte[] path = Arrays.copyOf(seed.getBytes(), 1024);
        helper(path, 0, 0, 0);
    }

    String shortestPath() throws Exception {
        return shortest;
    }

    String longestPath() throws Exception {
        return longest;
    }

    private void helper(byte[] path, int pathLength, int posX, int posY) {

        if (posX == 3 && posY == 3) {
            if (pathLength < shortestLength) {
                shortest = new String(path, seed.length(), pathLength);
                shortestLength = pathLength;
            }
            if (pathLength > longestLength) {
                longest = new String(path, seed.length(), pathLength);
                longestLength = pathLength;
            }
            return;
        }

        md5.reset();
        md5.update(path, 0, pathLength + seed.length());
        byte[] digest = md5.digest();

//        String tempPath = new String(path);

        // Up
        if (posY > 0 && ((digest[0] & 0xf0) >> 4) > 10) {
            path[pathLength + seed.length()] = 'U';
            helper(path, pathLength + 1, posX, posY - 1);
        }

        // Down
        if (posY < 3 && (digest[0] & 0x0f) > 10) {
            path[pathLength + seed.length()] = 'D';
            helper(path, pathLength + 1, posX, posY + 1);
        }

        // Left
        if (posX > 0 && ((digest[1] & 0xf0) >> 4) > 10) {
            path[pathLength + seed.length()] = 'L';
            helper(path, pathLength + 1, posX - 1, posY);
        }

        // Right
        if (posX < 3 && (digest[1] & 0x0f) > 10) {
            path[pathLength + seed.length()] = 'R';
            helper(path, pathLength + 1, posX + 1, posY);
        }
    }

    public static void main(String[] args) throws Exception {
        Day17Vault vault = new Day17Vault("qljzarfv");
        System.out.println("Shortest: " + vault.shortestPath());
        System.out.println("Longest: " + vault.longestPath().length());
    }
}
