package implementation.card.minion;

import java.util.ArrayList;

public class TheRipper extends Minion {
    public TheRipper(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public TheRipper(Minion minion) {
        super(minion);
    }

    public void action(Minion minion) {
        minion.setAttackDamage(Math.max(minion.getAttackDamage() - 2, 0));

        this.hasAttacked = true;
    }
}
