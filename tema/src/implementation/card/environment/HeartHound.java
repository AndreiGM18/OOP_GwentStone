package implementation.card.environment;

import implementation.card.utils.MaxbyHP;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public class HeartHound extends Environment {
    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    protected void action(ArrayList<Minion> minions, ArrayList<Minion> advMinions) {
        Minion maxMinion = minions.stream().max(new MaxbyHP()).get();

        minions.remove(maxMinion);

        advMinions.add(maxMinion);
    }
}