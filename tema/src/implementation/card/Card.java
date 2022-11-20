package implementation.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.card.hero.Hero;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public abstract class Card {
    protected final int mana;
    protected final String description;
    protected final ArrayList<String> colors;
    protected final String name;

    public Card(final int mana, final String description, final ArrayList<String> colors,
                final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    public final int getMana() {
        return mana;
    }

    public final String getDescription() {
        return description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final String getName() {
        return name;
    }

    /**
     * Creates a different ObjectNode based on what type of card it is
     * @return an ObjectNode for the output
     */
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

        if (this instanceof Hero) {
            o.put("health", ((Hero) this).getHealth());
        }

        return o;
    }

    /**
     *
     * @return colors ArrayNode
     */
    public ArrayNode createColorsNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode colorsArrayNode = objectMapper.createArrayNode();

        for (String color : colors) {
            colorsArrayNode.add(color);
        }

        return colorsArrayNode;
    }

    /**
     * Generic action function; is overridden
     */
    public void action() {
    }
}
