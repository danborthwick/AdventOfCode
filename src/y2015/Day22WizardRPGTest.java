package y2015;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day22WizardRPGTest {

    @Test
    public void testA1() throws Exception {
        //suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage
        Day22WizardRPG.GameState state = new Day22WizardRPG.GameState(13, 8, 10, 250);
        int actual = new Day22WizardRPG(false).leastManaToWin(state);
        assertThat(actual, is(226));
    }

    public Day22WizardRPG.GameState initialStateForA2() {
        return new Day22WizardRPG.GameState(14, 8, 10, 250);
    }

    @Test @Ignore
    public void testA2() throws Exception {
        //suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage
        //Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
        Day22WizardRPG.GameState state = initialStateForA2();
        int actual = new Day22WizardRPG(false).leastManaToWin(state);
        assertThat(actual, is(641));
    }

    @Test
    public void testA2Verbose() throws Exception {
        Day22WizardRPG.GameState state = initialStateForA2();
        Day22WizardRPG rpg = new Day22WizardRPG(false);

        state = rpg.stepBattleWithPlayerSpell(state, Day22WizardRPG.Spell.Recharge);
        assertThat(state, is(new Day22WizardRPG.GameState(14, 8, 2, 122)));

        state = rpg.stepBattleWithPlayerSpell(state, Day22WizardRPG.Spell.Shield);
        assertThat(state, is(new Day22WizardRPG.GameState(14, 8, 1, 211)));

        state = rpg.stepBattleWithPlayerSpell(state, Day22WizardRPG.Spell.Drain);
        assertThat(state, is(new Day22WizardRPG.GameState(12, 8, 2, 340)));

        state = rpg.stepBattleWithPlayerSpell(state, Day22WizardRPG.Spell.Poison);
        assertThat(state, is(new Day22WizardRPG.GameState(9, 8, 1, 167)));

        state = rpg.stepBattleWithPlayerSpell(state, Day22WizardRPG.Spell.MagicMissile);
        assertThat(state, is(new Day22WizardRPG.GameState(-1, 8, 1, 114)));
    }



}