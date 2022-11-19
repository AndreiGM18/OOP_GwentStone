package implementation.card.hero;

import constants.Constants;
import implementation.card.Card;

import java.util.ArrayList;

public abstract class Hero extends Card {
    protected int health = Constants.Integers.HERO_STARTING_HP;
    protected boolean hasAttacked = false;

    public Hero(final int mana, final String description, final ArrayList<String> colors,
                final String name) {
        super(mana, description, colors, name);
    }

    /**
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets tne new health
     * @param health new health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return whether the card has attacked or not
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Sets whether the card has attacked or not
     * @param hasAttacked whether the card has attacked or not
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
