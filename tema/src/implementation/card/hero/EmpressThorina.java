package implementation.card.hero;

import implementation.card.minion.Minion;
import implementation.card.utils.MaxbyHP;

import java.util.ArrayList;

public class EmpressThorina extends Hero {
    public EmpressThorina(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    protected void action(ArrayList<Minion> minions) {
        minions.stream().max(new MaxbyHP()).get().setHealth(0);
    }
}