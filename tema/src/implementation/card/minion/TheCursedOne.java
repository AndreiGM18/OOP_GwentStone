package implementation.card.minion;

import java.util.ArrayList;

public class TheCursedOne extends Minion {
    public TheCursedOne(final int mana, final String description, final ArrayList<String> colors,
                        final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public TheCursedOne(final Minion minion) {
        super(minion);
    }

    /**
     * Switches the given minion's HP and attackDamage
     * @param minion the given minion
     */
    public void action(final Minion minion) {
        int hp = minion.getHealth();
        int atk = minion.getAttackDamage();

        minion.setHealth(atk);
        minion.setAttackDamage(hp);

        this.hasAttacked = true;
    }
}
