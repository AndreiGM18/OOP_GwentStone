package implementation.card.environment;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public Firestorm(final int mana, final String description, final ArrayList<String> colors,
                     final String name) {
        super(mana, description, colors, name);
    }

    /**
     * -1 HP for all given minions
     * @param minions the given row
     */
    public void action(final ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setHealth(minion.getHealth() - 1);
        }
    }
}
