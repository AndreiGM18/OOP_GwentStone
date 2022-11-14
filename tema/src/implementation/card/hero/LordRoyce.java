package implementation.card.hero;

import implementation.card.minion.Minion;
import implementation.card.utils.MaxbyATK;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    protected void action(ArrayList<Minion> minions) {
        minions.stream().max(new MaxbyATK()).get().setFrozen(true);
    }
}