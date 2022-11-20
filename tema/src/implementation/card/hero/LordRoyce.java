package implementation.card.hero;

import implementation.card.minion.Minion;
import implementation.card.utils.MaxByATK;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(final int mana, final String description, final ArrayList<String> colors,
                     final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Sets the frozen parameter of the minion with highest attackDamage to true
     * @param minions the given row
     */
    public void action(final ArrayList<Minion> minions) {
        if (minions.stream().max(new MaxByATK()).isPresent()) {
            minions.stream().max(new MaxByATK()).get().setFrozen(true);
        }

        this.hasAttacked = true;
    }
}
