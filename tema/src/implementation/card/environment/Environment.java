package implementation.card.environment;

import implementation.card.Card;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public Environment(final int mana, final String description, final ArrayList<String> colors,
                       final String name) {
        super(mana, description, colors, name);
    }
}
