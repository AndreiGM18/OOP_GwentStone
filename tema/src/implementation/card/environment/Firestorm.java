package implementation.card.environment;

import implementation.card.minion.Minion;

import java.util.ArrayList;


public class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions)
            minion.setHealth(minion.getHealth() - 1);
    }
}
