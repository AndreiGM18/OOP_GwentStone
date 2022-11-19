package implementation.card.environment;

import implementation.card.utils.MaxByHP;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public class HeartHound extends Environment {
    public HeartHound(final int mana, final String description, final ArrayList<String> colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Steals the minion with the most HP, by adding it to advMinions and removing it from minions
     * @param minions the current player's row
     * @param advMinions the enemy player's row
     */
    public void action(final ArrayList<Minion> minions, final ArrayList<Minion> advMinions) {
        if (minions.stream().max(new MaxByHP()).isPresent()) {
            Minion maxMinion = minions.stream().max(new MaxByHP()).get();

            minions.add(maxMinion);

            advMinions.remove(maxMinion);
        }
    }
}
