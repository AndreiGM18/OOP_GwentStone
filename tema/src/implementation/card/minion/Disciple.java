package implementation.card.minion;

import java.util.ArrayList;

public class Disciple extends Minion {
    public Disciple(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }
}