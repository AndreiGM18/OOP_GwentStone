package implementation.card.environment;

import implementation.card.Card;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public Environment(final int mana, final String description, final ArrayList<String> colors,
                       final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Generic action function; is overridden
     * HeartHound is an exception
     * @param minions the row that the ability is used on
     */
    public void action(final ArrayList<Minion> minions) {
    }
}
