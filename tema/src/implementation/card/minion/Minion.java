package implementation.card.minion;

import implementation.card.Card;
import implementation.card.hero.Hero;

import java.util.ArrayList;

public class Minion extends Card {
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

    /**
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the new health
     * @param health new health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return attackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets the new attackDamage
     * @param attackDamage new attackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     *
     * @return whether the minion is frozen or not
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Sets the frozen status
     * @param frozen new frozen status
     */
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
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
     * @return whether the minion has attacked or not
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Sets whether the minion has attacked or not
     * @param hasAttacked whether the minion has attacked or not
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}






