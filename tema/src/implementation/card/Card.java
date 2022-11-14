package implementation.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.card.hero.Hero;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public abstract class Card {
    protected int mana;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;

    public Card(int mana, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectNode createCardNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode o = objectMapper.createObjectNode();

        o.put("mana", this.getMana());

        if (this instanceof Minion) {
            o.put("attackDamage", ((Minion) this).getAttackDamage());
            o.put("health", ((Minion) this).getHealth());
        }

        o.put("description", this.getDescription());

        ArrayNode colorsNode = this.createColorsNode();
        o.set("colors", colorsNode);

        o.put("name", this.getName());

        if (this instanceof Hero)
            o.put("health", ((Hero) this).getHealth());

        return o;
    }

    public ArrayNode createColorsNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode colorsArrayNode = objectMapper.createArrayNode();

        for (String color : colors) {
            colorsArrayNode.add(color);
        }

        return colorsArrayNode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "mana=" + mana +
                ", description='" + description + '\'' +
                ", colors=" + colors +
                ", name='" + name + '\'' +
                '}';
    }
}