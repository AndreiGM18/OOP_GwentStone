package implementation.card.minion;

import java.util.ArrayList;

public class TheCursedOne extends Minion {
    public TheCursedOne(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        int hp = minion.getHealth();

        this.health = this.health ^ hp;
        hp = this.health ^ minion.health;
        this.health = this.health ^ hp;

        minion.setHealth(hp);

        int atk = minion.getAttackDamage();

        this.attackDamage = this.attackDamage ^ atk;
        atk = this.attackDamage ^ atk;
        this.attackDamage = this.attackDamage ^ atk;

        minion.setAttackDamage(atk);
    }
}