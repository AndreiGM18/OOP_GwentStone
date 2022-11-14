package implementation.card.minion;

import implementation.card.Card;
import implementation.card.hero.Hero;

import java.util.ArrayList;

public class Minion extends Card {
    protected int health;
    protected int attackDamage;
    protected boolean frozen;
    protected boolean hasAttacked = false;

    public Minion(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, name);
        this.health = health;
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void attackMinion(Minion minion) {
        minion.setHealth(minion.getHealth() - this.attackDamage);
        this.hasAttacked = true;
    }

    public void attackHero(Hero hero) {
        hero.setHealth(hero.getHealth() - this.attackDamage);
        this.hasAttacked = true;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void action(Card card) {
    }
}






