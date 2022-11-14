package implementation.card.hero;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class KingMudface extends Hero {
    public KingMudface(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}