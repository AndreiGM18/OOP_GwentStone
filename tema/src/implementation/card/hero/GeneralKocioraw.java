package implementation.card.hero;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int mana, final String description, final ArrayList<String> colors,
                           final String name) {
        super(mana, description, colors, name);
    }

    /**
     * +1 attackDamage to all minions
     * @param minions the given row
     */
    public void action(final ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }

        this.hasAttacked = true;
    }
}
