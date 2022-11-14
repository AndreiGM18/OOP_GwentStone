package implementation.card.hero;

import implementation.card.minion.Minion;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void action(ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
    }
}