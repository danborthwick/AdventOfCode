package y2016;

import java.util.Arrays;

public class Day19ElfGifts {

    static int winner(int elfCount) {
        int[] giftCount = new int[elfCount];
        Arrays.fill(giftCount, 1);

        int giver;
        for (int taker = 0; ; taker = (taker + 1) % elfCount) {
            if (giftCount[taker] == 0) continue;

            for (giver = (taker + 1) % elfCount; giftCount[giver] == 0; giver = (giver + 1) % elfCount) {
            }

            giftCount[taker] += giftCount[giver];
            giftCount[giver] = 0;

            if (giftCount[taker] == elfCount) {
                return taker + 1;
            }

            taker = giver;
        }
    }

    static class Elf {

        public Elf prev, next;
        public int id;
        public Elf(int id) {
            this.id = id;
        }

        Elf skip(int offset) {
            Elf elf = this;
            for (int i=0; i < offset; i++) {
                elf = elf.next;
            }
            return elf;
        }

        public Elf destroy() {
            prev.next = this.next;
            next.prev = this.prev;
            return this.next;
        }

    }
    public static int winnerCenter(int elfCount) {
        Elf root = new Elf(1);
        Elf prev = root;

        for (int i=2; i <= elfCount; i++) {
            prev.next = new Elf(i);
            prev.next.prev = prev;
            prev = prev.next;
        }

        prev.next = root;
        root.prev = prev;

        Elf taker = root;
        Elf giver = taker.skip(elfCount / 2);

        while (taker.next != taker) {
            giver = giver.destroy();
            if (elfCount % 2 == 1) {
                giver = giver.next;
            }
            taker = taker.next;
            elfCount--;
        }
        return taker.id;
    }

    public static void main(String[] args) {
        System.out.println("Winner 3017957 is: " + winner(3017957));
        System.out.println("Winner center 3017957 is: " + winnerCenter(3017957));
    }
}
