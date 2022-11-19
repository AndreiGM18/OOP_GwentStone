package implementation.card.minion;

import java.util.ArrayList;

public class Miraj extends Minion {
    public Miraj(final int mana, final String description, final ArrayList<String> colors,
                 final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public Miraj(final Minion minion) {
        super(minion);
    }

    /**
     * Switches the given minion's HP with this minion's HP
     * @param minion the given minion
     */
    public void action(final Minion minion) {
        int hp = this.health;
        this.health = minion.getHealth();
        minion.setHealth(hp);

        this.hasAttacked = true;
    }
}
