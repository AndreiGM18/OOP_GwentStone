package implementation.card.minion;

import java.util.ArrayList;

public class TheCursedOne extends Minion {
    public TheCursedOne(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public void action(Minion minion) {
        int hp = minion.getHealth();
        int atk = minion.getAttackDamage();

        minion.setHealth(atk);
        minion.setAttackDamage(hp);

        this.hasAttacked = true;
    }
}