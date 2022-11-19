package implementation.card.minion;

import java.util.ArrayList;

public class Disciple extends Minion {
    public Disciple(final int mana, final String description, final ArrayList<String> colors,
                    final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public Disciple(final Minion minion) {
        super(minion);
    }

    /**
     * +2 HP to the given minion
     * @param minion the given minion
     */
    public void action(final Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
        this.hasAttacked = true;
    }
}
