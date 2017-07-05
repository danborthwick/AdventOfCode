package y2015;

public class Day22WizardRPG {

    int minFound = Integer.MAX_VALUE;
    boolean hardMode;

    public Day22WizardRPG(boolean hardMode) {
        this.hardMode = hardMode;
    }

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
        public int manaSpent = 0;
        public int shieldRemaining = 0;
        public int poisonRemaining = 0;
        public int rechargeRemaining = 0;

        public GameState(int bossHitPoints, int bossDamage, int playerHitPoints, int mana) {
            this.bossHitPoints = bossHitPoints;
            this.bossDamage = bossDamage;
            this.playerHitPoints = playerHitPoints;
            this.mana = mana;
        }

        public boolean canCast(Spell spell) {

            if (spell.cost() > mana)
                return false;

            switch (spell) {
                case Shield: return shieldRemaining <= 1;
                case Poison: return poisonRemaining <= 1;
                case Recharge: return rechargeRemaining <= 1;
                default: return true;
            }
        }

        private void cast(Spell spellToCast) {
            switch (spellToCast) {
                case MagicMissile:
                    bossHitPoints -= 4;
                    break;

                case Drain:
                    bossHitPoints -= 2;
                    playerHitPoints += 2;
                    break;

                case Shield:
                    shieldRemaining = 6;
                    break;

                case Poison:
                    poisonRemaining = 6;
                    break;

                case Recharge:
                    rechargeRemaining = 5;
                    break;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GameState gameState = (GameState) o;

            if (bossHitPoints != gameState.bossHitPoints) return false;
            if (bossDamage != gameState.bossDamage) return false;
            if (playerHitPoints != gameState.playerHitPoints) return false;
            return mana == gameState.mana;

        }

        @Override
        public int hashCode() {
            int result = bossHitPoints;
            result = 31 * result + bossDamage;
            result = 31 * result + playerHitPoints;
            result = 31 * result + mana;
            return result;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public boolean playerDead() {
            return playerHitPoints <= 0;
        }
    }

    public int leastManaToWin(GameState state) throws Exception {
        minFound = Integer.MAX_VALUE;
        leastManaToWinHelper(state);
        return minFound;
    }

    private void leastManaToWinHelper(GameState oldState) throws Exception {
        for (Spell spellToCast : Spell.values()) {

            if (!oldState.canCast(spellToCast))
                continue;

            GameState newState = stepBattleWithPlayerSpell(oldState, spellToCast);

            if (newState.bossHitPoints <= 0) {
                if (newState.manaSpent < minFound) {
                    minFound = newState.manaSpent;
                }
            }
            else if (!newState.playerDead()
                    && (newState.manaSpent < minFound)) {
                leastManaToWinHelper(newState);
            }
        }
    }

    public GameState stepBattleWithPlayerSpell(final GameState oldState, Spell playerSpell) throws Exception {
        GameState newState = (GameState) oldState.clone();

        if (hardMode) {
            newState.playerHitPoints--;
            if (newState.playerDead()) {
                return newState;
            }
        }

        applyEffectsReturnArmour(newState);

        newState.mana -= playerSpell.cost();
        newState.manaSpent = oldState.manaSpent + playerSpell.cost();

        // Terminate this branch of depth first search if we already have a better answer
        if (newState.manaSpent >= minFound) {
            return newState;
        }

        newState.cast(playerSpell);

        // Boss Turn
        int armour = applyEffectsReturnArmour(newState);
        if (newState.bossHitPoints > 0) {
            newState.playerHitPoints -= Math.max(newState.bossDamage - armour, 1);
        }

        return newState;
    }

    private int applyEffectsReturnArmour(GameState oldState) {
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
        GameState state = new GameState(58, 9, 50, 500);
        System.out.println("Easy: " + new Day22WizardRPG(false).leastManaToWin(state));
        System.out.println("Hard: " + new Day22WizardRPG(true).leastManaToWin(state));
    }
}
