package implementation.card.minion;

import java.util.ArrayList;

public class Miraj extends Minion {
    public Miraj(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        int hp = minion.getHealth();

        this.health = this.health ^ hp;
        hp = this.health ^ minion.health;
        this.health = this.health ^ hp;

        minion.setHealth(hp);
    }
}
