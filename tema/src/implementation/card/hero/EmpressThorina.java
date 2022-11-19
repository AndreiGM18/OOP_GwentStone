package implementation.card.hero;

import implementation.card.minion.Minion;
import implementation.card.utils.MaxByHP;

import java.util.ArrayList;

public class EmpressThorina extends Hero {
    public EmpressThorina(final int mana, final String description, final ArrayList<String> colors,
                          final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Removes the minion with the most HP
     * @param minions the given row
     */
    public void action(final ArrayList<Minion> minions) {
        if (minions.stream().max(new MaxByHP()).isPresent()) {
            minions.remove(minions.stream().max(new MaxByHP()).get());
        }
    }
}
