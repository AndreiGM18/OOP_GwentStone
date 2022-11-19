package implementation.card.environment;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public Winterfell(final int mana, final String description, final ArrayList<String> colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Freezes all given minions by changing their frozen variable
     * @param minions the given row
     */
    public void action(final ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setFrozen(true);
        }
    }
}
