package implementation.card.minion;

import java.util.ArrayList;

public class Miraj extends Minion {
    public Miraj(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    public void action(Minion minion) {
        int hp = this.health;
        int advHP = minion.getHealth();

        this.health = advHP;
        minion.setHealth(hp);

        this.hasAttacked = true;
    }
}
