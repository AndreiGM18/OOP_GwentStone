package implementation.card.hero;

import implementation.card.minion.Minion;
import implementation.card.utils.MaxbyATK;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void action(ArrayList<Minion> minions) {
        if (minions.size() == 1)
            minions.get(0).setFrozen(true);

        minions.stream().max(new MaxbyATK()).get().setFrozen(true);
    }
}