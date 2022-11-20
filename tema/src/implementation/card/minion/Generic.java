package implementation.card.minion;

import java.util.ArrayList;

/**
 * Used for Sentinel and Berserker
 */
public class Generic extends Minion {
    public Generic(final int mana, final String description, final ArrayList<String> colors,
                final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public Generic(final Minion minion) {
        super(minion);
    }
}
