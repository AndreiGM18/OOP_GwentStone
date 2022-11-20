package implementation.card.minion;

import implementation.card.Card;
import implementation.card.hero.Hero;

import java.util.ArrayList;

public abstract class Minion extends Card {
    protected int health;
    protected int attackDamage;
    protected boolean frozen;
    protected boolean hasAttacked = false;

    public Minion(final int mana, final String description, final ArrayList<String> colors,
                  final int health, final int attackDamage, final String name) {
        super(mana, description, colors, name);
        this.health = health;
        this.attackDamage = attackDamage;
    }

    public Minion(final Minion minion) {
        super(minion.mana, minion.description, minion.colors, minion.name);
        this.health = minion.health;
        this.attackDamage = minion.attackDamage;
        this.frozen = minion.frozen;
        this.hasAttacked = minion.hasAttacked;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    public final boolean getHasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Attacks a given minion
     * @param minion the given minion
     */
    public void attackMinion(final Minion minion) {
        minion.setHealth(Math.max(minion.getHealth() - this.attackDamage, 0));
        this.hasAttacked = true;
    }

    /**
     * Attacks a given hero
     * @param hero the given hero
     */
    public void attackHero(final Hero hero) {
        hero.setHealth(Math.max(hero.getHealth() - this.attackDamage, 0));
        this.hasAttacked = true;
    }

    /**
     *
     * @param minion
     */
    public void action(final Minion minion) {
    }
}
