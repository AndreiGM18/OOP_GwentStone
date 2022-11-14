package implementation.card.environment;

import implementation.card.Card;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public Environment(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }
}
