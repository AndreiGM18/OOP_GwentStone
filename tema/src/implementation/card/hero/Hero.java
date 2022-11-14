package implementation.card.hero;

import implementation.card.Card;

import java.util.ArrayList;

public abstract class Hero extends Card {
    protected int health = 30;
    protected boolean hasAttacked = false;

    public Hero(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}








