public class Day22WizardRPG {

    enum Spell {
        MagicMissile, Drain, Shield, Poison, Recharge;

        static final int[] costs = {53, 73, 113, 173, 229};

        public int cost() {
            return costs[this.ordinal()];
        }
    }

    static class GameState implements Cloneable {
        public int bossHitPoints;
        public int bossDamage;
        public int playerHitPoints;
        public int mana;
        public int shieldRemaining = 0;
        public int poisonRemaining = 0;
        public int rechargeRemaining = 0;

        public boolean isSpellActive(Spell spell) {
            return (spell == Spell.Shield && shieldRemaining > 0)
                    || (spell == Spell.Poison && poisonRemaining > 0)
                    || (spell == Spell.Recharge && rechargeRemaining > 0);
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static int leastManaToWin() throws Exception {

        GameState state = new GameState();
        state.bossHitPoints = 58;
        state.bossDamage = 9;
        state.playerHitPoints = 50;
        state.mana = 500;

        return helper(state, 0);
    }

    private static int helper(GameState oldState, int oldManaSpent) throws Exception {

        applyEffectsReturnArmour(oldState);
        int minManaSpent = Integer.MAX_VALUE;

        for (Spell spellToCast : Spell.values()) {

            if (oldState.isSpellActive(spellToCast)) {
                continue;
            }

            GameState newState = (GameState) oldState.clone();
            newState.mana -= spellToCast.cost();
            int newManaSpent = oldManaSpent + spellToCast.cost();

            switch (spellToCast) {
                case MagicMissile:
                    newState.bossHitPoints -= 4;
                    break;

                case Drain:
                    newState.bossHitPoints -= 2;
                    newState.playerHitPoints += 2;
                    break;

                case Shield:
                    newState.shieldRemaining = 6;
                    break;

                case Poison:
                    newState.poisonRemaining = 6;
                    break;

                case Recharge:
                    newState.rechargeRemaining = 5;
                    break;
            }


            // Boss Turn
            int armour = applyEffectsReturnArmour(newState);
           if (newState.bossHitPoints <= 0) {
                return newManaSpent;
            }

            newState.playerHitPoints -= Math.max(newState.bossDamage - armour, 1);

            if (newState.playerHitPoints <= 0) {
                return Integer.MAX_VALUE;
            }

            minManaSpent = Math.min(minManaSpent, helper(newState, newManaSpent));
        }

        return minManaSpent;
    }

    private static int applyEffectsReturnArmour(GameState oldState) {
        int armour = 0;

        if (oldState.shieldRemaining > 0) {
            armour = 7;
            oldState.shieldRemaining--;
        }
        if (oldState.poisonRemaining > 0) {
            oldState.bossHitPoints -= 3;
            oldState.poisonRemaining--;
        }
        if (oldState.rechargeRemaining > 0) {
            oldState.mana += 101;
            oldState.rechargeRemaining--;
        }
        return armour;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("A: " + leastManaToWin());
    }
}
