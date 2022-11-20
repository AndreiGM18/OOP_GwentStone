package implementation.card.hero;

import constants.Constants;
import implementation.card.Card;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public abstract class Hero extends Card {
    protected int health = Constants.Integers.HERO_STARTING_HP;
    protected boolean hasAttacked = false;

    public Hero(final int mana, final String description, final ArrayList<String> colors,
                final String name) {
        super(mana, description, colors, name);
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final boolean getHasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Generic action function; is overridden
     * @param minions the row on which the ability is used on
     */
    public void action(final ArrayList<Minion> minions) {
    }
}
