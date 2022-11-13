package implementation.card.environment;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public Winterfell(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions)
            minion.setFrozen(true);
    }
}