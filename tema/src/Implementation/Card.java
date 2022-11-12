package Implementation;

import java.util.ArrayList;

abstract class Card {
    protected int mana;
    protected String description;
    protected ArrayList<String> colors;
    protected void action() {
    }

    public Card(int mana, String description, ArrayList<String> colors) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}