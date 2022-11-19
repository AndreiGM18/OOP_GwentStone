package implementation.card.minion;

import java.util.ArrayList;

public class TheRipper extends Minion {
    public TheRipper(final int mana, final String description, final ArrayList<String> colors,
                     final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public TheRipper(final Minion minion) {
        super(minion);
    }

    /**
     * -2 attackDamage for the given minion
     * @param minion the given minion
     */
    public void action(final Minion minion) {
        minion.setAttackDamage(Math.max(minion.getAttackDamage() - 2, 0));

        this.hasAttacked = true;
    }
}
