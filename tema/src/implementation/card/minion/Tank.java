package implementation.card.minion;

import java.util.ArrayList;

public class Tank extends Minion {
    public Tank(final int mana, final String description, final ArrayList<String> colors,
                final int health, final int attackDamage, final String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public Tank(final Minion minion) {
        super(minion);
    }
}
