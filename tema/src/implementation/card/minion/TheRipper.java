package implementation.card.minion;

import java.util.ArrayList;

public class TheRipper extends Minion {
    public TheRipper(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }
    protected void action(Minion minion) {
        minion.setAttackDamage(getAttackDamage() - 2);
    }
}
