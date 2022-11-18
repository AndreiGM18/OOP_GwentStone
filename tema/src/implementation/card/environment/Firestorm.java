package implementation.card.environment;

import implementation.card.minion.Minion;

import java.util.ArrayList;


public class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void action(ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setHealth(minion.getHealth() - 1);
        }
    }
}
