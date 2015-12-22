import java.util.Arrays;

public class Day21RPG {

    static class Item {
        public final int cost;
        public final int damage;
        public final int armour;

        Item(int cost, int damage, int armour) {
            this.cost = cost;
            this.damage = damage;
            this.armour = armour;
        }
    }

    static final Item[] allWeapons = {
            new Item(8,     4,       0),
            new Item(10,     5,       0),
            new Item(25,     6,       0),
            new Item(40,     7,       0),
            new Item(74,     8,       0)
    };

    static final Item[] allArmour = {
            new Item(0, 0, 0),  //EMPTY
            new Item(13, 0, 1),
            new Item(31, 0, 2),
            new Item(53, 0, 3),
            new Item(75, 0, 4),
            new Item(102, 0, 5),
    };
    
    static final Item[] allRings = {
            new Item(0, 0, 0),  //EMPTY
            new Item(0, 0, 0),  //EMPTY
            new Item(25, 1, 0),
            new Item(50, 2, 0),
            new Item(100, 3, 0),
            new Item(20, 0, 1),
            new Item(40, 0, 2),
            new Item(80, 0, 3),
    };

    public static class Character {
        public final int initialHitPoints;
        public final int damage;
        public final int armour;
        public int remainingHitPoints;

        public Character(int hitPoints, int damage, int armour) {
            this.initialHitPoints = hitPoints;
            this.damage = damage;
            this.armour = armour;
            reset();
        }

        public void reset() {
            remainingHitPoints = initialHitPoints;
        }
    }

    public static int minWinCost(int playerHitPoints, Character boss) {

        int minCost = Integer.MAX_VALUE;

        for (Item weapon : allWeapons) {
            for (Item armour : allArmour) {
                for (Item ring1 : allRings) {
                    for (Item ring2 : allRings) {
                        Item[] items = { weapon, armour, ring1, ring2 };

                        int cost = Arrays.stream(items).mapToInt(i -> i.cost).sum();
                        if (cost < minCost) {
                            int damage = Arrays.stream(items).mapToInt(i -> i.damage).sum();
                            int totalArmour = Arrays.stream(items).mapToInt(i -> i.armour).sum();
                            Character player = new Character(playerHitPoints, damage, totalArmour);

                            if (winner(player, boss) == player) {
                                minCost = cost;
                            }
                        }
                    }
                }
            }
        }

        return minCost;
    }

    public static int maxLoseCost(int playerHitPoints, Character boss) {

        int maxCost = Integer.MIN_VALUE;

        for (Item weapon : allWeapons) {
            for (Item armour : allArmour) {
                for (Item ring1 : allRings) {
                    for (Item ring2 : allRings) {
                        Item[] items = { weapon, armour, ring1, ring2 };

                        int cost = Arrays.stream(items).mapToInt(i -> i.cost).sum();
                        if (cost > maxCost) {
                            int damage = Arrays.stream(items).mapToInt(i -> i.damage).sum();
                            int totalArmour = Arrays.stream(items).mapToInt(i -> i.armour).sum();
                            Character player = new Character(playerHitPoints, damage, totalArmour);

                            if (winner(player, boss) == boss) {
                                maxCost = cost;
                            }
                        }
                    }
                }
            }
        }

        return maxCost;
    }

    private static Character winner(Character player, Character boss) {
        player.reset();
        boss.reset();

        Character attacker = player, defender = boss;

        while (true) {
            int damage = Math.max(attacker.damage - defender.armour, 1);
            defender.remainingHitPoints -= damage;

            if (defender.remainingHitPoints <= 0)
                return attacker;

            Character temp = attacker;
            attacker = defender;
            defender = temp;
        }
    }

    public static void main(String[] args) {
        Character boss = new Character(104, 8, 1);
        System.out.println("MinCost: " + minWinCost(100, boss));
        System.out.println("MaxCost: " + maxLoseCost(100, boss));
    }
}
